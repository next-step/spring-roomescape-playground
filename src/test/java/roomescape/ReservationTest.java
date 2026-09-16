package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationTest {
    private LocalDate testDate = LocalDate.now().plusDays(1);
    private Long timeId;

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void set() {
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "15:40");
        timeId = jdbcTemplate.queryForObject("SELECT id FROM time WHERE time = '15:40'", Long.class);
        RestAssured.port = this.port;
    }


    @Test
    @DisplayName("새로운 예약을 정상적으로 등록할 수 있다")
    void createReservation() {

        RestAssured.given().log().all().contentType(ContentType.JSON)
                .body(createParams())
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .body("id", is(1));
    }

    @Test
    @DisplayName("등록된 예약을 조회할 수 있다")
    void readReservation() {
        saveReservation();

        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약을 삭제할 수 있다")
    void deleteReservation() {
        saveReservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("예약을 삭제하면 예약 목록에서 제거된다")
    void deleteReservationCheck() {
        //Given
        saveReservation();

        //When
        RestAssured.given().log().all()
                .when().delete("/reservations/1");

        //Then
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    private void saveReservation() {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(createParams())
                .when().post("/reservations");
    }

    @Test
    @DisplayName("timeId 대신 time 문자열로 요청 시 400 에러가 발생한다")
    void createReservation_WithLegacyTimeFormat_ThrowsException() {
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



    private Map<String, Object> createParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", testDate.toString());
        params.put("time", timeId);

        return params;
    }
}
