package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
class MissionStepTest {

    @Test
    @DisplayName("어드민 메인 페이지를 반환한다")
    void 일단계() {
        RestAssured.given().log().all()
                   .when().get("/")
                   .then().log().all()
                   .statusCode(200);
    }

    @Test
    @DisplayName("예약 관리 페이지를 반환한다")
    void 이단계_1() {
        RestAssured.given().log().all()
                   .when().get("/reservation")
                   .then().log().all()
                   .statusCode(200);
    }
}
