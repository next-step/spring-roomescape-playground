package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

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

    @Test
    @DisplayName("예약 목록을 조회한다")
    void 이단계_2() {
        RestAssured.given().log().all()
                   .when().get("/reservations")
                   .then().log().all()
                   .statusCode(200)
                   .body("size()", is((0)));
    }

    @Test
    @DisplayName("예약을 추가한다")
    void 삼단계_1() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                   .contentType(ContentType.JSON)
                   .body(params)
                   .when().post("/reservations")
                   .then().log().all()
                   .statusCode(201)
                   .header("Location", "/reservations/1")
                   .body("id", is(1));

        RestAssured.given().log().all()
                   .when().get("/reservations")
                   .then().log().all()
                   .statusCode(200)
                   .body("size()", is(1));
    }

    @Test
    @DisplayName("예약을 삭제한다")
    void 삼단계_2() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                   .contentType(ContentType.JSON)
                   .body(params)
                   .when().post("/reservations")
                   .then().log().all()
                   .statusCode(201)
                   .header("Location", "/reservations/1")
                   .body("id", is(1));

        RestAssured.given().log().all()
                   .when().get("/reservations")
                   .then().log().all()
                   .statusCode(200)
                   .body("size()", is(1));

        RestAssured.given().log().all()
                   .when().delete("/reservations/1")
                   .then().log().all()
                   .statusCode(204);

        RestAssured.given().log().all()
                   .when().get("/reservations")
                   .then().log().all()
                   .statusCode(200)
                   .body("size()", is(0));
    }
}
