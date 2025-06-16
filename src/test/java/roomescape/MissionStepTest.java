package roomescape;

import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Test
    @DisplayName("1단계 - 기본 URI로 요청 시 정상적으로 어드민 페이지가 반환된다.")
    void shouldReturnAdminPage_whenDefaultURI() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("2단계 - 예약 관리 페이지 URI로 요청 시 정상적으로 관리 페이지가 반환된다.")
    void shouldReturnReservationPage() {
        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("2단계 - 예약 목록 확인 URI로 요청 시 정상적으로 목록이 조회된다.")
    void shouldReturnReservationList() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }
}
