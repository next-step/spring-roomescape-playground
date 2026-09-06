package roomescape.controller;

import io.restassured.http.ContentType;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import roomescape.repository.DatabaseTest;

import java.util.HashMap;
import java.util.Map;

@DatabaseTest
class TimeControllerTest {

    @Test
    void 존재하지_않는_시간을_삭제하면_404가_반환된다() {
        RestAssured.given().log().all()
                .when().delete("/times/999")
                .then().log().all()
                .statusCode(404);
    }

    @Test
    void 시간을_생성하면_201이_반환된다() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    void 빈_값으로_시간을_생성하면_400이_반환된다() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 시간을_삭제하면_204가_반환된다() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "11:00");

        String location = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
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
                .when().delete("/times/abc")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void JSON_형식이_잘못되면_400이_반환된다() {
        String invalidJson = "{\"time\":\"10:00\"";  // 괄호 안 닫힘

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when().post("/times")
                .then().log().all()
                .statusCode(400);
    }
}
