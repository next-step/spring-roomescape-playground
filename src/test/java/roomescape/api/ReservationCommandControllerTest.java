package roomescape.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(value = {"classpath:init.sql", "classpath:test-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationCommandControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("새로운 예약을 추가할 수 있다.")
    void addReservation() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
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
    }

    @Test
    @DisplayName("새로운 예약을 추가하면 DB reservation table에 데이터가 추가된다.")
    void addReservation_jdbc() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1");

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("새로운 예약을 추가할 때 기존 API 스펙에 맞춰 요청을 보내면 상태코드 400을 반환한다.")
    void addReservation_fail_original_spec() {
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
    @DisplayName("추가하려는 예약의 정보 중 필요한 인자가 없는 경우 400 상태코드를 반환한다.")
    void addReservation_fail() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        // 필요한 인자가 없는 경우
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

    }

    @Test
    @DisplayName("특정 예약을 삭제할 수 있다.")
    void removeReservation() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .body("id", is(1));

        // when, then
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
    @DisplayName("특정 예약을 삭제하면 DB reservation table의 데이터가 삭제된다.")
    void removeReservation_jdbc() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1");

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @Test
    @DisplayName("삭제하려는 예약이 존재하지 않을 경우 400 상태코드를 반환한다.")
    void removeReservation_fail() {
        // 삭제할 예약이 없는 경우
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(400);
    }
}
