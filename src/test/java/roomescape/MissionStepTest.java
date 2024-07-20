package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.dto.ReservationRequestDto;
import roomescape.dto.TimeRequestDto;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationController reservationController;

    @DisplayName("루트 경로 확인")
    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약 조회 확인")
    @Test
    void 이단계() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0)); // 아직 생성 요청이 없으니 Controller에서 임의로 넣어준 Reservation 갯수 만큼 검증하거나 0개임을 확인하세요.
    }

    @DisplayName("예약 생성, 조회, 삭제 확인")
    @Test
    void 삼단계() {
        ReservationRequestDto requestDto = new ReservationRequestDto("브라운", "2023-08-05", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .body("id", is(1))
                .body("name", equalTo("브라운"))
                .body("date", equalTo("2023-08-05"))
                .body("time.time", equalTo("15:40"));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].name", equalTo("브라운"))
                .body("[0].date", equalTo("2023-08-05"))
                .body("[0].time.time", equalTo("15:40"));

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

    @DisplayName("예외 처리 확인")
    @Test
    void 사단계() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        // 필요한 인자가 없는 경우
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);

        // 삭제할 예약이 없는 경우
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(404);
    }

    @DisplayName("데이터 베이스 연결 및 테이블 확인")
    @Test
    void 오단계() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("데이터 조회 확인")
    @Test
    void 육단계() {
        // 시간 데이터 생성
        TimeRequestDto timeRequestDto = new TimeRequestDto("15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeRequestDto)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/times/1");

        Long timeId = jdbcTemplate.queryForObject("SELECT id FROM time WHERE time = ?", new Object[]{"15:40"}, Long.class);

        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", timeId);

        List<Map<String, Object>> reservations = jdbcTemplate.queryForList("SELECT r.id, r.name, r.date, t.time FROM reservation r INNER JOIN time t ON r.time_id = t.id");

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);

        // 검증 로직 추가
        for (Map<String, Object> reservation : reservations) {
            assertThat(reservation.get("name")).isEqualTo("브라운");
            assertThat(reservation.get("date")).isEqualTo("2023-08-05");
            assertThat(reservation.get("time")).isEqualTo("15:40");
        }
    }

    @DisplayName("데이터 추가 및 삭제 확인")
    @Test
    void 칠단계() {
        // 시간 데이터 생성
        TimeRequestDto timeRequestDto = new TimeRequestDto("10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeRequestDto)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/times/1");

        ReservationRequestDto requestDto = new ReservationRequestDto("브라운", "2023-08-05", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestDto)
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

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    @DisplayName("시간 관리 기능")
    @Test
    void 팔단계() {
        TimeRequestDto timeRequestDto = new TimeRequestDto("10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeRequestDto)
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

    @DisplayName("기존 코드 수정 확인")
    @Test
    void 구단계() {
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", "10:0");  // 잘못된 시간 형식

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @DisplayName("계층화 확인")
    @Test
    void 십단계() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }
}
