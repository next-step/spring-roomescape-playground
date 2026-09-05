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
import java.time.LocalDate;
import java.time.LocalTime;
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
    Long 시간_생성(String time) {
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
                .getLong("id");
    }

    void 예약_생성(String name, String date, Long timeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("date", date);
        params.put("time", timeId);

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then()
                .statusCode(201);
    }

    @Test
    void 이단계() {
        Long firstTimeId = 시간_생성("10:00");
        Long secondTimeId = 시간_생성("11:00");
        Long thirdTimeId = 시간_생성("12:00");

        예약_생성("브라운", LocalDate.now().plusDays(1).toString(), firstTimeId);
        예약_생성("브라운", LocalDate.now().plusDays(2).toString(), secondTimeId);
        예약_생성("브라운", LocalDate.now().plusDays(3).toString(), thirdTimeId);

        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3))
                .body("[0].date", is(LocalDate.now().plusDays(1).toString()))
                .body("[0].time.id", is(1))
                .body("[0].time.time", is("10:00"));
    }

    @Test
    void 삼단계() {
        Long timeId = 시간_생성("15:40");
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", LocalDate.now().plusDays(1).toString());
        params.put("time", timeId);

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
        Long timeId = 시간_생성("15:40");
        Map<String, Object> params = new HashMap<>();
        params.put("date", LocalDate.now().plusDays(1).toString());
        params.put("time", timeId);

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
        Long timeId = 시간_생성("15:40");
        Map<String, Object> dateParams = new HashMap<>();
        dateParams.put("name", "브라운");
        dateParams.put("date", "날짜");
        dateParams.put("time", timeId);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(dateParams)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body(is("날짜 또는 시간 형식이 올바르지 않습니다."));

        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("name", "브라운");
        timeParams.put("date", LocalDate.now().plusDays(1).toString());
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
        Long timeId = 시간_생성("10:00");
        Map<String, Object> params = new HashMap<>();
        params.put("name", "a".repeat(21));
        params.put("date", LocalDate.now().plusDays(1).toString());
        params.put("time", timeId);

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
        Long timeId = 시간_생성("10:00");
        예약_생성("브라운", LocalDate.now().plusDays(1).toString(), timeId);

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
        Long timeId = 시간_생성("10:00");
        예약_생성("브라운", LocalDate.now().plusDays(1).toString(), timeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", LocalDate.now().plusDays(1).toString());
        params.put("time", timeId);

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
        Long timeId = 시간_생성("10:00");
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", LocalDate.now().minusDays(1).toString());
        params.put("time", timeId);

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
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", LocalTime.of(15, 40));
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", LocalDate.now().plusDays(1).toString(), 1L);

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
        Long timeId = 시간_생성("10:00");
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", LocalDate.now().plusDays(1).toString());
        params.put("time", timeId);

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
        reservation.put("date", LocalDate.now().plusDays(1).toString());
        reservation.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 예약_시간이_누락되면_400을_응답한다() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", LocalDate.now().plusDays(1).toString());

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then()
                .statusCode(400)
                .body(is("예약 시간을 선택해야 합니다."));
    }

    @Test
    void 시간_등록시_시간이_누락되면_400을_응답한다() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body("{}")
                .when().post("/times")
                .then()
                .statusCode(400)
                .body(is("시간을 입력해야 합니다."));
    }

    @Test
    void 예약에서_사용_중인_시간은_삭제할_수_없다() {
        Long timeId = 시간_생성("10:00");
        예약_생성("브라운", LocalDate.now().plusDays(1).toString(), timeId);

        RestAssured.given()
                .when().delete("/times/" + timeId)
                .then()
                .statusCode(409)
                .body(is(
                        "예약에서 사용 중인 시간은 삭제할 수 없습니다. 관련 예약을 먼저 취소해 주세요."
                ));
    }

    @Test
    void 동일한_시간은_중복_등록할_수_없다() {
        시간_생성("10:00");

        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then()
                .statusCode(409)
                .body(is("이미 등록된 시간입니다."));
    }

    @Test
    void 시간이_지난_과거_예약도_조회할_수_있다() {
        Long timeId = 시간_생성("10:00");

        jdbcTemplate.update(
                "insert into reservation (name, date, time_id) values (?, ?, ?)",
                "브라운",
                LocalDate.now().minusDays(1).toString(),
                timeId
        );

        RestAssured.given()
                .when().get("/reservations")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].date", is(LocalDate.now().minusDays(1).toString()));
    }
}
