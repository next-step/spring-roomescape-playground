package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    //Spring MVC 1~4단계
    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    void 예약_생성(String name, String date, String time) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("date", date);
        params.put("time", time);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then()
                .statusCode(201);
    }

    @Test
    void 이단계() {
        예약_생성("브라운", "2027-01-01", "10:00");
        예약_생성("브라운", "2027-01-02", "11:00");
        예약_생성("브라운", "2027-01-03", "12:00");

        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3))
                .body("[0].date", is("2027-01-01"))
                .body("[0].time", is("10:00"));
    }

    @Test
    void 삼단계() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2027-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 사단계() {
        Map<String, String> params = new HashMap<>();
        params.put("date", "2023-01-01");
        params.put("time", "15:40");

        // 필요한 인자가 없는 경우
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body(is("예약 정보는 모두 입력해야 합니다."));

        // 삭제할 예약이 없는 경우
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(404)
                .body(is("삭제할 예약을 찾을 수 없습니다."));
    }

    @Test
    void 날짜_또는_시간_올바르지_못한_형식() {
        Map<String, String> dateParams = new HashMap<>();
        dateParams.put("name", "브라운");
        dateParams.put("date", "날짜");
        dateParams.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(dateParams)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body(is("날짜 또는 시간 형식이 올바르지 않습니다."));

        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("name", "브라운");
        timeParams.put("date", "2027-01-01");
        timeParams.put("time", "시간");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeParams)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body(is("날짜 또는 시간 형식이 올바르지 않습니다."));
    }

    @Test
    void 예약자_이름은_20자를_초과할_수_없다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "a".repeat(21));
        params.put("date", "2027-01-01");
        params.put("time", "10:00");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then()
                .statusCode(400)
                .body(is("예약자 이름은 20자 이하여야 합니다."));
    }

    @Test
    void 예약을_1개_조회한다() {
        예약_생성("브라운", "2027-01-01", "10:00");

        RestAssured.given().log().all()
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("name", is("브라운"));
    }

    @Test
    void 존재하지_않는_예약을_조회하면_404를_응답한다() {
        RestAssured.given()
                .when().get("/reservations/1")
                .then()
                .statusCode(404)
                .body(is("조회할 예약을 찾을 수 없습니다."));
    }

    @Test
    void 동일한_날짜와_시간에는_중복_예약할_수_없다() {
        예약_생성("브라운", "2027-01-01", "10:00");

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2027-01-01");
        params.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(409)
                .body(is("이미 해당 날짜와 시간에 예약이 존재합니다."));
    }

    @Test
    void 과거_날짜에는_예약할_수_없다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-01-01");
        params.put("time", "10:00");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then()
                .statusCode(400)
                .body(is("올바른 예약 날짜와 시간을 선택해야 합니다."));
    }

    // Spring MVC 5~7단계
    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2027-08-05", "15:40");

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    void 칠단계() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2027-08-05");
        params.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1");

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
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
                .body("size()", is(1))
                .body("[0].id", is(1))
                .body("[0].time", is("10:00"));

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void 존재하지_않는_시간을_삭제하면_404를_응답한다() {
        RestAssured.given()
                .when().delete("/times/1")
                .then()
                .statusCode(404)
                .body(is("삭제할 시간을 찾을 수 없습니다."));
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
}
