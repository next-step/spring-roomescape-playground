package roomescape.controller;

import io.restassured.http.ContentType;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import roomescape.repository.DatabaseTest;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

@DatabaseTest
class ReservationControllerTest {
    @Test
    void 존재하지_않는_예약을_삭제하면_404가_반환된다() {
        RestAssured.given().log().all()
                .when().delete("/reservations/999")
                .then().log().all()
                .statusCode(404);
    }

    @Test
    void 예약을_생성하면_201이_반환된다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2026-08-20");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    void 빈_값으로_예약을_생성하면_400이_반환된다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body(equalTo("날짜와 시간을 입력해주세요."));
    }

    @Test
    void 예약을_삭제하면_204가_반환된다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2026-08-21");
        params.put("time", "16:00");

        String location = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .extract().header("Location");

        RestAssured.given().log().all()
                .when().delete(location)
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void id가_숫자가_아니면_400이_반환된다() {
        RestAssured.given().log().all()
                .when().delete("/reservations/abc")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void JSON_형식이_잘못되면_400이_반환된다() {
        String invalidJson = "{\"name\":\"브라운\",\"date\":\"2026-08-20\"";  // 괄호 안 닫힘

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }
}
