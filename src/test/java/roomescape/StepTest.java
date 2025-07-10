package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationController reservationController;

    @Test
    void 삼단계() {
        // Given - 시간 등록
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "15:40");

        // When - 예약 등록
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", 1);

        // Then - id는 1
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(201)
            .header("Location", "/reservations/1")
            .body("id", is(1));

        // Then - 목록에 존재
        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(1));

        // When - 삭제
        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(204);

        // Then - 목록에 없어야 함
        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(0));
    }

    @Test
    void 사단계() {
        // Given - 빈 값이 들어간 잘못된 요청
        ReservationRequest invalidRequest = new ReservationRequest(
            "브라운",
            null,
            null
        );

        // 필요한 인자가 없는 경우
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(invalidRequest)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(400);

        // 삭제할 예약이 없는 경우
        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(404);
    }

    @Test
    @DisplayName("존재하지 않는 예약 id를 입력 시 예외를 던진다")
    void throw_exception_when_delete_doesnt_exits_reservation_id() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        // 삭제할 예약이 없는 경우
        RestAssured.given().log().all()
            .when().delete("/reservations/7")
            .then().log().all()
            .statusCode(404)
            .body("code", equalTo("reservation.notFound"))
            .body("message", equalTo("존재하지 않는 예약입니다. id : 7"))
            .body("arguments.id", equalTo(7));
    }

    @Test
    void 오단계() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null)
                .next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 육단계() {
        // Given: 시간과 예약 데이터를 DB에 직접 삽입
        jdbcTemplate.update("insert into time (time) values (?)", "15:40");

        Long timeId = jdbcTemplate.queryForObject(
            "select id from time where time = ?", Long.class, "15:40"
        );

        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운",
            "2023-08-05", timeId);

        // When: GET /reservations 요청
        List<ReservationResponse> reservations = RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200).extract()
            .jsonPath().getList(".", ReservationResponse.class);

        // Then: DB의 예약 개수와 응답 개수가 일치해야 함
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation",
            Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    void 칠단계() {
        // Given - 10:00 시간 등록
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "10:00");

        // When - 예약 생성 요청
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", 1);

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(201)
            .header("Location", "/reservations/1");

        // Then - 등록된 예약 확인
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation",
            Integer.class);
        assertThat(count).isEqualTo(1);

        // When - 삭제
        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(204);

        // Then - 삭제된 예약 확인
        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation",
            Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @Test
    void 팔단계() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/times")
            .then().log().all()
            .statusCode(201)
            .header("Location", "/times/1");

        RestAssured.given().log().all()
            .when().get("/times")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(1));

        RestAssured.given().log().all()
            .when().delete("/times/1")
            .then().log().all()
            .statusCode(204);
    }

    @Test
    void 구단계() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", 1);

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(reservation)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(404);
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
}
