package roomescape.helper;

import static io.restassured.http.ContentType.JSON;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import java.util.Map;

public class TestHelper {

    public static ValidatableResponse getMethodTest(String path, int expectedStatusCode) {
        return RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(expectedStatusCode);
    }

    public static ValidatableResponse postMethodTest(Map<String, String> params, String path, int expectedStatusCode) {
        return RestAssured.given().log().all()
                .contentType(JSON)
                .body(params)
                .when().post(path)
                .then().log().all()
                .statusCode(expectedStatusCode);
    }

    public static ValidatableResponse deleteMethodTest(String path, long id, int expectedStatusCode) {
        return RestAssured.given().log().all()
                .when().delete(path + "/" + id)
                .then().log().all()
                .statusCode(expectedStatusCode);
    }
}
