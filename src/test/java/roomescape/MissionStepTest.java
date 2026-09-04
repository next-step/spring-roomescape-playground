package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(MissionStepTest.FixedClockConfiguration.class)
class MissionStepTest {

    @TestConfiguration
    static class FixedClockConfiguration {

        @Bean
        @Primary
        Clock fixedClock() {
            return Clock.fixed(
                    Instant.parse("2026-08-12T03:00:00Z"),
                    ZoneId.of("Asia/Seoul")
            );
        }
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationController reservationController;

    @Test
    void 일단계() {
        RestAssured.get("/").then().statusCode(200);
    }

    @Test
    void 이단계() {
        RestAssured.get("/reservation").then().statusCode(200);
        RestAssured.get("/reservations").then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(0));
    }

    @Test
    void 삼단계() {
        createTime("15:40");

        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2026-08-13");
        reservation.put("time", "1");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .body("id", is(1))
                .body("name", is("브라운"))
                .body("date", is("2026-08-13"))
                .body("time.id", is(1))
                .body("time.time", is("15:40"));

        RestAssured.get("/reservations").then().statusCode(200).body("size()", is(1));
        RestAssured.delete("/reservations/1").then().statusCode(204);
        RestAssured.get("/reservations").then().statusCode(200).body("size()", is(0));
    }

    @Test
    void 사단계() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "");
        reservation.put("time", "");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(reservation)
                .post("/reservations")
                .then().statusCode(400);
        RestAssured.delete("/reservations/1").then().statusCode(404);
    }

    @Test
    @DisplayName("이름이 공백인 예약은 추가할 수 없다")
    void rejectsReservationWithBlankName() {
        createTime("15:40");

        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", " ");
        reservation.put("date", "2026-08-13");
        reservation.put("time", 1);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(reservation)
                .post("/reservations")
                .then()
                .statusCode(400)
                .body("message", is("예약자 이름은 필수입니다."));
    }

    @Test
    @DisplayName("지난 일시로는 예약할 수 없다")
    void rejectsPastReservation() {
        createTime("11:59");

        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2026-08-12");
        reservation.put("time", 1);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(reservation)
                .post("/reservations")
                .then()
                .statusCode(400)
                .body("message", is("지난 일시로는 예약할 수 없습니다."));
    }

    @Test
    void 오단계() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
            assertThat(connection.getMetaData().getTables(null, null, "TIME", null).next()).isTrue();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Test
    void 육단계() {
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "15:40");
        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2026-08-13", 1L
        );

        RestAssured.get("/reservations").then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].time.id", is(1))
                .body("[0].time.time", is("15:40"));
    }

    @Test
    void 칠단계() {
        createTime("10:00");

        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2026-08-13");
        reservation.put("time", 1);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(reservation)
                .post("/reservations")
                .then().statusCode(201).header("Location", "/reservations/1");

        assertThat(countReservations()).isEqualTo(1);
        RestAssured.delete("/reservations/1").then().statusCode(204);
        assertThat(countReservations()).isZero();
    }

    @Test
    void 팔단계() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .post("/times")
                .then().statusCode(201);

        RestAssured.get("/times").then().statusCode(200).body("size()", is(1));
        RestAssured.delete("/times/1").then().statusCode(204);
    }

    @Test
    void 구단계() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 십단계() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

    private void createTime(String time) {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of("time", time))
                .post("/times")
                .then().statusCode(201);
    }

    private int countReservations() {
        return jdbcTemplate.queryForObject("SELECT count(1) FROM reservation", Integer.class);
    }
}
