package roomescape;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    @DisplayName("일단계")
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("이단계")
    void 이단계() {

        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);

        Integer initialCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM reservation", Integer.class);

        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "11:00");
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "12:00");

        Long timeId1 = jdbcTemplate.queryForObject("SELECT id FROM time WHERE time = ?", Long.class,
                "10:00");
        assertThat(timeId1).isNotNull();
        Long timeId2 = jdbcTemplate.queryForObject("SELECT id FROM time WHERE time = ?", Long.class,
                "11:00");
        assertThat(timeId2).isNotNull();
        Long timeId3 = jdbcTemplate.queryForObject("SELECT id FROM time WHERE time = ?", Long.class,
                "12:00");
        assertThat(timeId3).isNotNull();

        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "사용자1", "2025-02-23",
                timeId1);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "사용자2", "2025-02-23",
                timeId2);
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "사용자3", "2025-02-23",
                timeId3);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(initialCount + 3));
    }

    @Test
    @DisplayName("삼단계")
    void 삼단계() {

        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("time", "15:40");

        int timeId = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeParams)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/times/1")
                .extract().path("id");

        Map<String, Object> timeObject = new HashMap<>();
        timeObject.put("id", timeId);
        timeObject.put("time", "15:40");

        Map<String, Object> reservationParams = new HashMap<>();
        reservationParams.put("name", "브라운");
        reservationParams.put("date", "2023-08-05");
        reservationParams.put("time", timeObject);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationParams)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .body("id", is(1))
        ;

        //getReservation 공백반환...
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

        // 삭제할 예약이 없는 경우
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(400);
    }

}
