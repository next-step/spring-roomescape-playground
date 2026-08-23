package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void returnBadRequestWhenReservationRequestIsInvalid() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "",
                            "date": "",
                            "time": ""
                        }
                        """)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("message", equalTo("예약 정보가 올바르지 않습니다."));
    }

    @Test
    void returnNotFoundWhenReservationDoesNotExist() {
        RestAssured.given().log().all()
                .when().delete("/reservations/999999")
                .then().log().all()
                .statusCode(404)
                .body("message", equalTo("예약을 찾을 수 없습니다."));
    }
}
