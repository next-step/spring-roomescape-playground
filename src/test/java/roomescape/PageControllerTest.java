package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.containsString;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class PageControllerTest {

    @Test
    void 메인_페이지_요청_시_HTML을_반환한다() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200)
                .contentType(containsString("text/html"));
    }

    @Test
    void 예약_관리_페이지_요청_시_HTML을_반환한다() {
        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200)
                .contentType(containsString("text/html"));
    }
}
