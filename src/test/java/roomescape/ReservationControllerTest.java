package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 예약_목록_json_반환() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 예약_추가() {
        시간을_추가한다();

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .body("id", is(1))
                .body("time.id", is(1))
                .body("time.time", is("15:40"));
    }

    @Test
    void 예약_취소() {
        예약을_추가한다();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void 예외_처리() {
        Map<String, String> blank = new HashMap<>();
        blank.put("name", "브라운");
        blank.put("date", "");
        blank.put("timeId", "");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(blank)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        Map<String, String> negativeTimeId = new HashMap<>();
        negativeTimeId.put("name", "브라운");
        negativeTimeId.put("date", "2023-08-05");
        negativeTimeId.put("timeId", "-1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(negativeTimeId)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        Map<String, String> notFoundTime = new HashMap<>();
        notFoundTime.put("name", "브라운");
        notFoundTime.put("date", "2023-08-05");
        notFoundTime.put("timeId", "999");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(notFoundTime)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(404);

        예약을_추가한다();

        Map<String, String> duplicated = new HashMap<>();
        duplicated.put("name", "네오");
        duplicated.put("date", "2023-08-05");
        duplicated.put("timeId", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(duplicated)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(409);
    }

    @Test
    void 예약_목록_데이터베이스에서_조회_테스트() {
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", LocalTime.of(15, 40));
        jdbcTemplate.update("INSERT INTO reservation (name, reserved_date, time_id) VALUES (?, ?, ?)",
                "브라운", LocalDate.of(2023, 8, 5), 1L);

        List<ReservationResponse> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationResponse.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);

        ReservationResponse reservation = reservations.get(0);
        assertThat(reservation.time().id()).isEqualTo(1L);
        assertThat(reservation.time().time()).isEqualTo("15:40");
    }

    @Test
    void 예약_추가_데이터베이스에_저장_테스트() {
        예약을_추가한다();

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void 예약_취소_데이터베이스에서_삭제_테스트() {
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", LocalTime.of(15, 40));
        jdbcTemplate.update("INSERT INTO reservation (name, reserved_date, time_id) VALUES (?, ?, ?)",
                "브라운", LocalDate.of(2023, 8, 5), 1L);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(0);
    }

    @Test
    void 기존_시간_형식으로_예약_추가시_예외_처리() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 컨트롤러_데이터베이스_로직_분리_테스트() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

    private void 예약을_추가한다() {
        시간을_추가한다();

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", "1");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().statusCode(201);
    }

    private void 시간을_추가한다() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "15:40");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().statusCode(201);
    }
}
