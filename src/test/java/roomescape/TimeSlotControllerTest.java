package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TimeSlotControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 시간_목록_json_반환() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 시간_추가() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/times/1")
                .body("id", is(1));
    }

    @Test
    void 시간_삭제() {
        시간을_추가한다();

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void 예외_처리() {
        Map<String, String> blank = new HashMap<>();
        blank.put("time", "");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(blank)
                .when().post("/times")
                .then().log().all()
                .statusCode(400);

        Map<String, String> invalidFormat = new HashMap<>();
        invalidFormat.put("time", "10시00분");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(invalidFormat)
                .when().post("/times")
                .then().log().all()
                .statusCode(400);

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(404);

        시간을_추가한다();

        Map<String, String> duplicated = new HashMap<>();
        duplicated.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(duplicated)
                .when().post("/times")
                .then().log().all()
                .statusCode(409);
    }

    private void 시간을_추가한다() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().statusCode(201);
    }
}
