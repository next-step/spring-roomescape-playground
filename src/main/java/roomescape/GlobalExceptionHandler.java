package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MissionStepTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void createReservation() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "test",
                            "date": "2026-08-22",
                            "time": "10:00"
                        }
                        """)
                .when()
                .post("/reservations")
                .then()
                .statusCode(201);
    }

    @Test
    void returnBadRequestWhenReservationRequestIsInvalid() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "",
                            "date": "",
                            "time": ""
                        }
                        """)
                .when()
                .post("/reservations")
                .then()
                .statusCode(400)
                .body("message",
                        equalTo("예약 정보가 올바르지 않습니다."));
    }

    @Test
    void returnNotFoundWhenReservationDoesNotExist() {
        given()
                .when()
                .delete("/reservations/999999")
                .then()
                .statusCode(404)
                .body("message",
                        equalTo("예약을 찾을 수 없습니다."));
    }
}
