package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private ReservationController reservationController;

    @Test
    @DisplayName("로그인한 사용자가 이름 없이 예약을 생성하면 자신의 이름으로 예약된다")
    void createReservationWithLoggedInUserName() {
        // given: 예약을 위한 시간 생성 및 로그인 토큰 발급
        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("time", "11:00");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeParams)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);

        String token = createToken("admin@email.com", "password");

        // when: 로그인 상태에서 'name' 없이 예약을 요청
        Map<String, Object> paramsWithoutName = new HashMap<>();
        paramsWithoutName.put("date", "2025-08-05");
        paramsWithoutName.put("timeId", 1L);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(paramsWithoutName)
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        // then: 로그인한 사용자('어드민')의 이름으로 예약이 생성됨
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.jsonPath().getString("name")).isEqualTo("어드민");
    }

    @Test
    @DisplayName("로그인한 사용자가 이름을 직접 입력하면 해당 이름으로 예약된다")
    void createReservationWithSpecifiedNameWhileLoggedIn() {
        // given: 예약을 위한 시간 생성 및 로그인 토큰 발급
        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("time", "11:00");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeParams)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);

        String token = createToken("admin@email.com", "password");

        // when: 로그인 상태에서 'name'을 직접 입력하여 예약을 요청
        Map<String, Object> paramsWithName = new HashMap<>();
        paramsWithName.put("name", "브라운");
        paramsWithName.put("date", "2025-08-06");
        paramsWithName.put("timeId", 1L);

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(paramsWithName)
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        // then: 직접 입력한 이름('브라운')으로 예약이 생성됨
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.jsonPath().getString("name")).isEqualTo("브라운");
    }

    @Test
    @DisplayName("로그인에 성공하고, 발급된 토큰으로 사용자 정보를 정상적으로 조회한다")
    void loginAndCheckUserStatus() {
        String token = createToken("admin@email.com", "password");
        assertThat(token).isNotBlank();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/login/check")
                .then().log().all()
                .statusCode(200)
                .body("name", is("어드민"));
    }

    @Test
    @DisplayName("홈 페이지 접근 시 정상 응답을 반환한다")
    void getHomePageReturnsOk() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 조회 페이지와 API가 정상적으로 동작한다")
    void getReservationPageAndList() {
        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("예약을 생성하고 조회하고 삭제할 수 있다")
    void createReadAndDeleteReservation() {
        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("time", "17:00");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeParams)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);

        Map<String, Object> reservationParams = new HashMap<>();
        reservationParams.put("name", "오찌");
        reservationParams.put("date", LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE));
        reservationParams.put("timeId", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationParams)
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

    @Test
    @DisplayName("유효하지 않은 예약 생성 또는 삭제 시 에러를 반환한다")
    void createOrDeleteReservationWithInvalidInputReturnsError() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("시간을 생성하고 조회하고 삭제할 수 있다")
    void createReadAndDeleteTime() {
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

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("등록되지 않은 시간으로 예약을 시도하면 400 에러를 반환한다")
    void createReservationWithUnregisteredTimeFails() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2025-08-05");
        reservation.put("timeId", 999L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("컨트롤러는 JdbcTemplate에 직접 의존하지 않아야 한다")
    void controllerShouldNotDependOnJdbcTemplate() {
        boolean isJdbcTemplateInjected = false;
        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }
        assertThat(isJdbcTemplateInjected).isFalse();
    }

    private String createToken(String email, String password) {
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("email", email);
        loginParams.put("password", password);

        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginParams)
                .when().post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .cookie("token");
    }
}
