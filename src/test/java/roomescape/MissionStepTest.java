package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import java.lang.reflect.Field;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void returnBadRequestWhenReservationRequestIsInvalid() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "",
                            "date": "",
                            "time": ""
                        }
                        """)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("message", equalTo("예약 정보가 올바르지 않습니다."));
    }

    @Test
    void returnNotFoundWhenReservationDoesNotExist() {
        RestAssured.given().log().all()
                .when().delete("/reservations/999999")
                .then().log().all()
                .statusCode(404)
                .body("message", equalTo("예약을 찾을 수 없습니다."));
    }

    @Test
    void 오단계() {
        try (Connection connection =
                     jdbcTemplate.getDataSource().getConnection()) {

            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");

            assertThat(
                    connection.getMetaData()
                            .getTables(null, null, "RESERVATION", null)
                            .next()
            ).isTrue();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void sevenStep() {
        int timeId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of("time", "10:00"))
                .when().post("/times")
                .then().statusCode(201)
                .extract().path("id");

        Map<String, Object> params = new HashMap<>();

        params.put("name", "브라운");
        params.put(
                "date",
                java.time.LocalDate.now(java.time.ZoneId.of("Asia/Seoul"))
                        .plusDays(1)
                        .toString()
        );
        params.put("time", timeId);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .body("time.id", equalTo(timeId))
                .body("time.time", equalTo("10:00"));

        Integer count = jdbcTemplate.queryForObject(
                "SELECT count(1) FROM reservation",
                Integer.class
        );

        assertThat(count).isEqualTo(1);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject(
                "SELECT count(1) FROM reservation",
                Integer.class
        );

        assertThat(countAfterDelete).isEqualTo(0);
    }

    @Test
    void 팔단계() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then()
                .statusCode(201)
                .header("Location", "/times/1")
                .body("time", equalTo("10:00"));

        RestAssured.given()
                .when().get("/times")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1));

        RestAssured.given()
                .when().delete("/times/1")
                .then()
                .statusCode(204);

        RestAssured.given()
                .when().get("/times")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    void 구단계() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2099-08-05");
        reservation.put("time", "10:00");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then()
                .statusCode(400);
    }

    @Test
    void 존재하지_않는_시간으로_예약할_수_없다() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2099-08-05");
        reservation.put("time", 999);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then()
                .statusCode(400);
    }

    @Autowired
    private ReservationController reservationController;

    @Test
    void 십단계() {
        boolean isJdbcTemplateInjected = false;

        for (Field field
                : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

    @Test
    void 예약에서_사용_중인_시간은_삭제할_수_없다() {
        // 1. 예약 시간을 등록한다.
        int timeId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of("time", "10:00"))
                .when().post("/times")
                .then()
                .statusCode(201)
                .extract().path("id");

        // 2. 등록된 시간으로 예약한다.
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put(
                "date",
                java.time.LocalDate.now(java.time.ZoneId.of("Asia/Seoul"))
                        .plusDays(1)
                        .toString()
        );
        reservation.put("time", timeId);

        int reservationId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then()
                .statusCode(201)
                .extract().path("id");

        // 3. 사용 중인 시간을 삭제하면 409와 오류 메시지를 반환한다.
        RestAssured.given()
                .when().delete("/times/" + timeId)
                .then()
                .statusCode(409)
                .body(
                        "message",
                        equalTo("예약에서 사용 중인 시간은 삭제할 수 없습니다.")
                );

        // 4. 삭제 실패 후에도 시간과 예약 정보가 유지된다.
        RestAssured.given()
                .when().get("/times")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(timeId));

        RestAssured.given()
                .when().get("/reservations/" + reservationId)
                .then()
                .statusCode(200)
                .body("time.id", equalTo(timeId));

        // 5. 예약을 삭제하면 해당 시간도 삭제할 수 있다.
        RestAssured.given()
                .when().delete("/reservations/" + reservationId)
                .then()
                .statusCode(204);

        RestAssured.given()
                .when().delete("/times/" + timeId)
                .then()
                .statusCode(204);

        RestAssured.given()
                .when().get("/times")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    void 잘못된_시간_형식으로_예약_시간을_등록할_수_없다() {
        String[] invalidTimes = {
                "",
                "ab:cd",
                "99:99",
                "24:00",
                "10:60",
                "9:00",
                "10:00:30"
        };

        for (String invalidTime : invalidTimes) {
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(Map.of("time", invalidTime))
                    .when().post("/times")
                    .then()
                    .statusCode(400);
        }

        RestAssured.given()
                .when().get("/times")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }

    @Test
    void 예약_날짜가_저장과_조회_후에도_유지된다() {
        int timeId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of("time", "10:00"))
                .when().post("/times")
                .then()
                .statusCode(201)
                .extract().path("id");

        String date = java.time.LocalDate.now(
                java.time.ZoneId.of("Asia/Seoul")
        ).plusDays(1).toString();

        Map<String, Object> request = new HashMap<>();
        request.put("name", "브라운");
        request.put("date", date);
        request.put("time", timeId);

        int reservationId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then()
                .statusCode(201)
                .body("date", equalTo(date))
                .extract().path("id");

        RestAssured.given()
                .when().get("/reservations/" + reservationId)
                .then()
                .statusCode(200)
                .body("date", equalTo(date))
                .body("time.id", equalTo(timeId));
    }
}
