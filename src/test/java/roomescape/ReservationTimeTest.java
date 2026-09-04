package roomescape;

import static org.hamcrest.core.Is.is;

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

public class ReservationTimeTest {

  @Test
  @DisplayName("POST /times 요청 시 201 상태 코드를 반환하는지 테스트")
  void test_시간_생성시_201을_반환하는지_테스트() {
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
  @DisplayName("GET /times 요청 시 저장된 시간 목록을 반환하는지 테스트")
  void test_시간_목록_조회시_저장된_시간을_반환하는지_테스트() {
    Map<String, String> params = new HashMap<>();
    params.put("time", "10:00");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/times");

    RestAssured.given().log().all()
        .when().get("/times")
        .then().log().all()
        .statusCode(200)
        .body("size()", is(1));
  }

  @Test
  @DisplayName("DELETE /times/{id} 요청 시 204 상태 코드를 반환하는지 테스트")
  void test_시간_삭제시_204를_반환하는지_테스트() {
    Map<String, String> params = new HashMap<>();
    params.put("time", "10:00");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/times");

    RestAssured.given().log().all()
        .when().delete("/times/1")
        .then().log().all()
        .statusCode(204);
  }

  @Test
  @DisplayName("POST /times 요청 시 이미 등록된 시간이면 409 상태 코드를 반환하는지 테스트")
  void test_시간_생성시_중복된_시간이면_409를_반환하는지_테스트() {
    Map<String, String> params = new HashMap<>();
    params.put("time", "10:00");

    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/times");

    RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/times")
        .then().log().all()
        .statusCode(409);
  }

  @Test
  @DisplayName("DELETE /times/{id} 요청 시 예약이 등록된 시간이면 409 상태 코드를 반환하는지 테스트")
  void test_시간_삭제시_예약이_등록된_시간이면_409를_반환하는지_테스트() {
    Map<String, String> timeParams = new HashMap<>();
    timeParams.put("time", "10:00");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(timeParams)
        .when().post("/times");

    Map<String, String> reservationParams = new HashMap<>();
    reservationParams.put("name", "브라운");
    reservationParams.put("date", "2023-08-05");
    reservationParams.put("time", "1");
    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(reservationParams)
        .when().post("/reservations");

    RestAssured.given().log().all()
        .when().delete("/times/1")
        .then().log().all()
        .statusCode(409);
  }
}
