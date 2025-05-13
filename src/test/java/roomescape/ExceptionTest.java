package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ExceptionTest {

    @Test
    void 입력_형식_오류() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "전서희");
        params.put("date", "2026-13-40");
        params.put("time", "13:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("error", is("InvalidFormat Exception : 입력 형식이 올바르지 않습니다."));
    }

    @Test
    void 요청_형식_오류() {
        String invalidJson = "{ name: 전서희, date: 2026-05-13, time: 09:40 }";

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(invalidJson)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("error", is("HTTP Not Readable Excetption : 요청 형식이 올바르지 않습니다."));
    }

    @Test
    void 중복_예약_예외() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "전서희");
        params.put("date", "2026-05-13");
        params.put("time", "09:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("error", is("동일한 예약이 이미 존재합니다."));
    }

}
