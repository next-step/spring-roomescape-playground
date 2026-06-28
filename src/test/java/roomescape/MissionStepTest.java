package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.controller.ReservationController;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcOperations jdbcTemplate;

    @Test
    void 일단계() {
        RestAssured.given()
                .when().get("/")
                .then()
                .statusCode(200);
    }

    @Test
    void 예약_시간은_0시를_허용한다() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "00:40");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then()
                .statusCode(201);
    }

    @Test
    void 예약_시간은_24시를_초과할_수_없다() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "25:40");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then()
                .statusCode(400)
                .body("message", is("잘못된 요청 본문입니다."));
    }

    @Test
    void 중복된_예약_시간은_추가할_수_없다() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then()
                .statusCode(400)
                .body("message", is("이미 존재하는 시간입니다."));
    }


    @Test
    void 사단계() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("timeId", "");

        // 필요한 인자가 없는 경우
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then()
                .statusCode(400)
                .body("message", is("날짜는 필수 입력값입니다."));

        // 삭제할 예약이 없는 경우
        RestAssured.given()
                .when().delete("/reservations/1")
                .then()
                .statusCode(400)
                .body("message", is("존재하지 않는 예약입니다."));
    }
    @Test
    void 팔단계() {
        Integer initialCount = jdbcTemplate.queryForObject("SELECT count(1) from time", Integer.class);

        Map<String, String> params = new HashMap<>();
        params.put("time", "20:00");

        Integer timeId = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then()
                .statusCode(201)
                .header("Location", "/times/" + (initialCount + 1))
                .extract()
                .jsonPath()
                .getInt("id");

        RestAssured.given()
                .when().get("/times")
                .then()
                .statusCode(200)
                .body("size()", is(initialCount + 1));

        RestAssured.given()
                .when().delete("/times/" + timeId)
                .then()
                .statusCode(204);
    }

    @Test
    void 구단계() {
        Integer timeId = createTime("20:00");

        jdbcTemplate.update(
                "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운",
                "2999-08-05",
                timeId
        );

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        RestAssured.given()
                .when().get("/reservations")
                .then()
                .statusCode(200)
                .body("size()", is(count))
                .body("[0].time.id", is(timeId))
                .body("[0].time.time", is("20:00"));

        RestAssured.given()
                .when().delete("/reservations/1")
                .then()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);

        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2999-08-05");
        reservation.put("timeId", timeId);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then()
                .statusCode(201)
                .header("Location", "/reservations/2")
                .body("id", is(2))
                .body("time.id", is(timeId))
                .body("time.time", is("20:00"));

        Integer countAfterCreate = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterCreate).isEqualTo(1);

        RestAssured.given()
                .when().get("/reservations")
                .then()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given()
                .when().delete("/reservations/2")
                .then()
                .statusCode(204);

        RestAssured.given()
                .when().get("/reservations")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

    private Integer createTime(String time) {
        Map<String, String> params = new HashMap<>();
        params.put("time", time);

        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getInt("id");
    }

    @Autowired
    private ReservationController reservationController;

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
