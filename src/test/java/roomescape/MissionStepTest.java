package roomescape;

import static org.hamcrest.CoreMatchers.is;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Test
    @DisplayName("localhost:8080 요청 시 home 화면을 반환하는지 테스트")
    void test_home_요청시_home_화면을_반환하는지_테스트() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("/reservation 요청 시 reservation 화면을 반환하는지 테스트")
    void test_reservation_요청시_reservation_화면을_반환하는지_테스트() {
        RestAssured.given().log().all()
            .when().get("/reservation")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    @DisplayName("/reservations 요청 시 저장된 예약 3건을 반환하는지 테스트")
    void test_reservations_요청시_저장된_예약_3건을_반환하는지_테스트() {
        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(3));
    }
}
