package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Test
    @DisplayName("/home은 어드민 페이지를 반환한다")
    void viewHome() {
        RestAssured.given().log().all()
                .when().get("/home")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("/reservation 예약페이지를 반환한다")
    void viewReservation() {
        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 리스트 확인")
    void reservationList() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3))
                .body("[0].id", equalTo(1))
                .body("[1].id", equalTo(2))
                .body("[2].id", equalTo(3))
                .body("[0].name", equalTo("지수"))
                .body("[1].name", equalTo("치수"))
                .body("[2].name", equalTo("찌수"));
    }
}
