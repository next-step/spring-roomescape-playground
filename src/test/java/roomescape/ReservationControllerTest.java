package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {
    @Test
    void 예약_목록_조회_요청_시_JSON_배열을_반환한다() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("size()", is(3))
                .body("[0].keySet()", containsInAnyOrder("id", "name", "date", "time"));
    }

    @Test
    void 예약_목록이_비어있으면_빈_JSON_배열을_반환한다() {
        // given
        List<Integer> ids = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("id");

        for (Integer id : ids) {
            RestAssured.given().log().all()
                    .when().delete("/reservations/" + id)
                    .then().log().all()
                    .statusCode(204);
        }

        // when & then
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .contentType(containsString("application/json"))
                .body("size()", is(0));
    }

    @Test
    void 예약_추가_요청이_성공하면_상태코드_201과_생성된_예약을_반환한다() {
        // given
        String date = LocalDate.now().plusDays(1).toString();
        String request = createReservationRequest(date, "브라운", "10:00");

        // when & then
        RestAssured.given().log().all()
                .contentType("application/json")
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .contentType(containsString("application/json"))
                .body("id", notNullValue())
                .body("name", is("브라운"))
                .body("date", is(date))
                .body("time", is("10:00"));
    }

    @Test
    void 날짜_형식이_올바르지_않으면_400을_반환한다() {
        // given
        String request = createReservationRequest("2026/08/12", "브라운", "10:00");

        // when & then
        assertBadRequest(request);
    }

    @Test
    void 시간_형식이_올바르지_않으면_400을_반환한다() {
        // given
        String date = LocalDate.now().plusDays(1).toString();
        String request = createReservationRequest(date, "브라운", "10-00");

        // when & then
        assertBadRequest(request);
    }

    @Test
    void 이름이_비어있으면_400을_반환한다() {
        // given
        String date = LocalDate.now().plusDays(1).toString();
        String request = createReservationRequest(date, "", "10:00");

        // when & then
        assertBadRequest(request);
    }

    @Test
    void 이름에_숫자나_특수기호가_포함되면_400을_반환한다() {
        // given
        String date = LocalDate.now().plusDays(1).toString();
        String request = createReservationRequest(date, "브라운1", "10:00");

        // when & then
        assertBadRequest(request);
    }

    @Test
    void 예약_삭제_요청에_성공하면_상태코드_204를_반환한다() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void 예약_삭제_요청_id가_숫자가_아니면_400을_반환한다() {
        RestAssured.given().log().all()
                .when().delete("/reservations/abc")
                .then().log().all()
                .statusCode(400);
    }

    private void assertBadRequest(String request) {
        RestAssured.given().log().all()
                .contentType("application/json")
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    private String createReservationRequest(String date, String name, String time) {
        return """
                {
                    "date": "%s",
                    "name": "%s",
                    "time": "%s"
                }
                """.formatted(date, name, time);
    }
}
