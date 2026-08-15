package roomescape.controller;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
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
                .body("[0].time", is("10:00"));
    }
}
