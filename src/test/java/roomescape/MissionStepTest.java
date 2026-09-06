package roomescape;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ReservationController reservationController;

  @Test
  @DisplayName("홈 요청 시 200 OK를 반환하는지 테스트")
  void test_home_요청시_200_OK를_반환하는지_테스트() {
    RestAssured.given().log().all()
        .when().get("/")
        .then().log().all()
        .statusCode(200);
  }

  @Test
  @DisplayName("/reservation 요청 시 reservation 화면을 반환하는지 테스트")
  void test_reservation_요청시_reservation_화면을_반환하는지_테스트() {
    RestAssured.given().log().all()
        .when().get("/reservation")
        .then().log().all()
        .statusCode(200);
  }

  @Test
  @DisplayName("/reservations 요청 시 200 상태 코드와 JSON 형식의 응답을 반환하는지 테스트")
  void test_reservations_요청시_정상_응답을_반환하는지_테스트() {
    RestAssured.given().log().all()
        .when().get("/reservations")
        .then().log().all()
        .statusCode(200)
        .contentType(ContentType.JSON);
  }

  @Test
  @DisplayName("POST /reservations 요청 시 201 상태 코드를 반환하는지 테스트")
  void test_post_요청시_예약이_생성되는지_테스트() {
    jdbcTemplate.update("INSERT INTO reservation_time(time) VALUES (?)", "15:40");

    Map<String, String> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2030-01-01");
    params.put("time", "1");

    RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/reservations")
        .then().log().all()
        .statusCode(201);
  }

  @Test
  @DisplayName("DELETE /reservations/{id} 요청 시 204 상태 코드를 반환하는지 테스트")
  void test_delete_요청시_예약이_삭제되는지_테스트() {
    jdbcTemplate.update("INSERT INTO reservation_time(time) VALUES (?)", "15:40");

    Map<String, String> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2030-01-01");
    params.put("time", "1");

    RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/reservations");

    RestAssured.given().log().all()
        .when().delete("/reservations/1")
        .then().log().all()
        .statusCode(204);
  }

  @Test
  @DisplayName("POST /reservations 요청 시 필수값이 공백이면 400 상태 코드를 반환하는지 테스트")
  void test_post_요청시_필수값이_공백이면_400을_반환하는지_테스트() {
    jdbcTemplate.update("INSERT INTO reservation_time(time) VALUES (?)", "10:00");

    Map<String, String> params = new HashMap<>();
    params.put("name", "");
    params.put("date", "2030-01-01");
    params.put("time", "1");

    RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/reservations")
        .then().log().all()
        .statusCode(400);
  }

  @Test
  @DisplayName("DELETE /reservations/{id} 요청 시 존재하지 않는 예약이면 404 상태 코드를 반환하는지 테스트")
  void test_delete_요청시_존재하지_않는_예약이면_404를_반환하는지_테스트() {
    RestAssured.given().log().all()
        .when().delete("/reservations/1")
        .then().log().all()
        .statusCode(404);
  }

  @Test
  @DisplayName("데이터베이스 및 reservation 테이블 연결 확인 테스트")
  void test_데이터베이스_연결과_테이블_존재를_확인하는지_테스트() {
    try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
      assertThat(connection).isNotNull();
      assertThat(connection.getCatalog()).isEqualTo("DATABASE");
      assertThat(
          connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @DisplayName("예약 조회 API가 데이터베이스에 저장된 예약을 반환하는지 테스트")
  void test_예약_조회_API가_데이터베이스를_조회하는지_테스트() {
    jdbcTemplate.update("INSERT INTO reservation_time(time) VALUES (?)", "15:40");
    jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운",
        "2030-01-01", 1);

    List<Reservation> reservations = RestAssured.given().log().all()
        .when().get("/reservations")
        .then().log().all()
        .statusCode(200).extract()
        .jsonPath().getList(".", Reservation.class);

    Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

    assertThat(reservations.size()).isEqualTo(count);
  }

  @Test
  @DisplayName("예약 생성 및 삭제 API가 데이터베이스에 반영되는지 테스트")
  void test_예약_생성과_삭제가_데이터베이스에_반영되는지_테스트() {
    jdbcTemplate.update("INSERT INTO reservation_time(time) VALUES (?)", "10:00");

    Map<String, String> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2030-01-01");
    params.put("time", "1");

    RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/reservations")
        .then().log().all()
        .statusCode(201)
        .header("Location", "/reservations/1");

    Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
    assertThat(count).isEqualTo(1);

    RestAssured.given().log().all()
        .when().delete("/reservations/1")
        .then().log().all()
        .statusCode(204);

    Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation",
        Integer.class);
    assertThat(countAfterDelete).isEqualTo(0);
  }

  @Test
  @DisplayName("기존 예약 추가 스펙(시간 문자열)으로 요청하면 400 상태 코드를 반환하는지 테스트")
  void test_post_요청시_기존_스펙의_시간_형식으로_요청하면_400을_반환하는지_테스트() {
    Map<String, String> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2030-01-01");
    params.put("time", "10:00");

    RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/reservations")
        .then().log().all()
        .statusCode(400);
  }

  @Test
  @DisplayName("ReservationController에 JdbcTemplate 필드가 없어 데이터베이스 접근 책임이 분리되었는지 테스트")
  void test_데이터베이스_접근_책임이_컨트롤러에서_분리되었는지_테스트() {
    boolean isJdbcTemplateInjected = false;

    for (Field field : reservationController.getClass().getDeclaredFields()) {
      if (field.getType().equals(JdbcTemplate.class)) {
        isJdbcTemplateInjected = true;
        break;
      }
    }

    assertThat(isJdbcTemplateInjected).isFalse();
  }

  @Test
  @DisplayName("POST /reservations 요청 시 존재하지 않는 시간 id면 404 상태 코드를 반환하는지 테스트")
  void test_post_요청시_존재하지_않는_시간_id면_404를_반환하는지_테스트() {
    Map<String, String> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2030-01-01");
    params.put("time", "1");

    RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/reservations")
        .then().log().all()
        .statusCode(404);
  }

  @Test
  @DisplayName("POST /reservations 요청 시 같은 날짜와 시간에 이미 예약이 있으면 409 상태 코드를 반환하는지 테스트")
  void test_post_요청시_중복_예약이면_409를_반환하는지_테스트() {
    jdbcTemplate.update("INSERT INTO reservation_time(time) VALUES (?)", "10:00");

    Map<String, String> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2030-01-01");
    params.put("time", "1");

    RestAssured.given()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/reservations");

    RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/reservations")
        .then().log().all()
        .statusCode(409);
  }

  @Test
  @DisplayName("POST /reservations 요청 시 과거 날짜면 400 상태 코드를 반환하는지 테스트")
  void test_post_요청시_과거_날짜면_400을_반환하는지_테스트() {
    jdbcTemplate.update("INSERT INTO reservation_time(time) VALUES (?)", "10:00");

    Map<String, String> params = new HashMap<>();
    params.put("name", "브라운");
    params.put("date", "2020-01-01");
    params.put("time", "1");

    RestAssured.given().log().all()
        .contentType(ContentType.JSON)
        .body(params)
        .when().post("/reservations")
        .then().log().all()
        .statusCode(400);
  }
}
