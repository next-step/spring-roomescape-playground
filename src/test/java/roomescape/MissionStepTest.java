package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Test
    @DisplayName("홈 요청 시 200 OK를 반환하는지 테스트")
    void test_home_요청시_200_OK를_반환하는지_테스트() {
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
    @DisplayName("/reservations 요청 시 200 상태 코드와 JSON 형식의 응답을 반환하는지 테스트")
    void test_reservations_요청시_정상_응답을_반환하는지_테스트() {
        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    @DisplayName("POST /reservations 요청 시 201 상태 코드를 반환하는지 테스트")
    void test_post_요청시_예약이_생성되는지_테스트() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(201);
    }

    @Test
    @DisplayName("DELETE /reservations/{id} 요청 시 204 상태 코드를 반환하는지 테스트")
    void test_delete_요청시_예약이_삭제되는지_테스트(){

        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations");

        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(204);
    }
}
