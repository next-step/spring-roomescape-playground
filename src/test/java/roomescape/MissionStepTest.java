package roomescape;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.Is.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import roomescape.controller.ReservationController;
import roomescape.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationController reservationController;

    private static void 메인_페이지_호출() {
        RestAssured.given().log().all()
            .when().get("/")
            .then().log().all()
            .statusCode(200);
    }

    private static void 예약_조회_값_사이즈_비교_로직(int reservationSize) {
        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(reservationSize));
    }

    private static void 예약_페이지_호출() {
        RestAssured.given().log().all()
            .when().get("/reservation")
            .then().log().all()
            .statusCode(200);
    }

    private static void 예약_삭제_성공_로직(int id) {
        RestAssured.given().log().all()
            .when().delete("/reservations/" + id)
            .then().log().all()
            .statusCode(204);
    }

    private static void 예약_저장_성공_로직(Map<String, String> params) {
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(201)
            .header("Location", "/reservations/1")
            .body("id", is(1));
    }

    private static void 시간_저장_성공_로직(Map<String, String> timeParams) {
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(timeParams)
            .when().post("/times")
            .then().log().all()
            .statusCode(201)
            .header("Location", "/times/1");
    }

    private static void 예약_삭제_실패_로직(int id) {
        RestAssured.given().log().all()
            .when().delete("/reservations/" + id)
            .then().log().all()
            .statusCode(400);
    }

    private static void 예약_저장_실패_로직(Map<String, String> params) {
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(400);
    }

    private static void 시간_삭제_성공_로직(int id) {
        RestAssured.given().log().all()
            .when().delete("/times/" + id)
            .then().log().all()
            .statusCode(204);
    }

    private static void 시간_조회_값_사이즈_비교_로직(int timeSize) {
        RestAssured.given().log().all()
            .when().get("/times")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(timeSize));
    }

    @Test
    void 일단계() {
        메인_페이지_호출();
    }

    @Test
    void 이단계() {
        예약_페이지_호출();
        예약_조회_값_사이즈_비교_로직(0);
    }

    @Test
    void 삼단계() {
        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("time", "10:00");

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "1");

        시간_저장_성공_로직(timeParams);
        예약_저장_성공_로직(params);
        예약_조회_값_사이즈_비교_로직(1);
        예약_삭제_성공_로직(1);
        예약_조회_값_사이즈_비교_로직(0);
    }

    @Test
    void 사단계() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        // 필요한 인자가 없는 경우

        예약_저장_실패_로직(params);
        예약_삭제_실패_로직(1);
    }

    @Test
    void 오단계() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 육단계() {
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "12:12");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05",
            "1");

        List<ReservationResponse> reservations = RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200).extract()
            .jsonPath().getList(".", ReservationResponse.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    void 칠단계() {
        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("time", "10:00");

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "1");

        시간_저장_성공_로직(timeParams);
        예약_저장_성공_로직(params);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);

        예약_삭제_성공_로직(1);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @Test
    void 팔단계() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        시간_저장_성공_로직(params);
        시간_조회_값_사이즈_비교_로직(1);
        시간_삭제_성공_로직(1);
    }

    @Test
    void 구단계() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", "10:00");

        예약_저장_실패_로직(reservation);
    }

    @Test
    void 구단계_Success_Case() {
        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("time", "10:00");

        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", "1");

        시간_저장_성공_로직(timeParams);
        예약_저장_성공_로직(reservation);
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