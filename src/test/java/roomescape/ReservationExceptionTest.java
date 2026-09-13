package roomescape;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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




@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationExceptionTest {
    @LocalServerPort
    int port;

    @Test
    @DisplayName("필요한 인자가 없는 경우 예외가 발생한다")
    void createException() {
        RestAssured.port = this.port;
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(createParams())
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("삭제할 예약이 없는 경우 예외가 발생한다")
    void deleteException() {
        RestAssured.port = this.port;
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(404);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("DB에 접근이 실패할 경우 예외가 발생한다.")
    void runtimeExeptionTest() {
        RestAssured.port = this.port;
        try {
            jdbcTemplate.execute("ALTER TABLE reservation RENAME TO reservation_tmp");
            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(500);
        } finally {
            jdbcTemplate.execute("ALTER TABLE reservation_tmp RENAME TO reservation");

        }
    }

    @Test
    @DisplayName("서버가 기대한 자료형과 다른 타입이 들어오면 400에러가 발생한다.")
    void handleIllegalArgumentException() {
        RestAssured.port = this.port;
        Map<String, String> params = new HashMap<>();
        params.put("time", "invalid");

        RestAssured.given().log().all().contentType(ContentType.JSON)
                .body(params).when().post("/times")
                .then().log().all().statusCode(400);
    }

    @Test
    @DisplayName("매개변수 타입과 요청된 데이터 타입이 일치하지 않으면 400에러가 발생한다.")
    void handleMethodArgumentMismatch() {
        RestAssured.port = this.port;
        RestAssured.given().log().all().contentType(ContentType.JSON).body(createParams()).delete("/reservations/abc")
                .then().log().all().statusCode(400);
    }

    @Test
    @DisplayName("외래키 제약 위반이 발생하면 409에러가 발생한다.")
    void exception1() {
        LocalDate testDate = LocalDate.now().plusDays(1);
        RestAssured.port = this.port;
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "10:00");
        Long timeId = jdbcTemplate.queryForObject("SELECT id FROM time WHERE time = '10:00'", Long.class);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", testDate.toString());
        params.put("time", timeId);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all().when().delete("times/1")
                .then().log().all().statusCode(409);
    }

    private Map<String, String> createParams() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        return params;
    }
}
