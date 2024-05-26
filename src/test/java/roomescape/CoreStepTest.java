package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CoreStepTest {

    @Test
    void 팔단계() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00");

        RestAssured.given().log().all()
                   .contentType(ContentType.JSON)
                   .body(params)
                   .when().post("/times")
                   .then().log().all()
                   .statusCode(201)
                   .header("Location", "/times/1");

        RestAssured.given().log().all()
                   .when().get("/times")
                   .then().log().all()
                   .statusCode(200)
                   .body("size()", is(1));

        RestAssured.given().log().all()
                   .when().delete("/times/1")
                   .then().log().all()
                   .statusCode(204);
    }
}
