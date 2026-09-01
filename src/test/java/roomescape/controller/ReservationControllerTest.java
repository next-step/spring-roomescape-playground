package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:reservation-controller-test"
)
@Sql(
        scripts = "/reservation-test-data.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class ReservationControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 예약_목록을_조회할_수_있다() {
        given()
                .when()
                .get("/reservations")
                .then()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    void 예약_시간은_HH_mm_형식으로_응답한다() {
        given()
                .when()
                .get("/reservations")
                .then()
                .statusCode(200)
                .body("[0].time", is("10:00"));
    }

    @Test
    void 예약을_추가할_수_있다() {
        String date = LocalDate.now().plusDays(1).toString();
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", date);
        params.put("time", "12:00");

        given()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("/reservations")
                .then()
                .statusCode(201)
                .header("Location", "/reservations/4")
                .body("id", is(4))
                .body("name", is("브라운"))
                .body("date", is(date))
                .body("time", is("12:00"));
    }

    @Test
    void 예약을_삭제할_수_있다() {
        given()
                .when()
                .delete("/reservations/1")
                .then()
                .statusCode(204);
    }
}
