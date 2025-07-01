package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.controller.dto.ResponseReservation;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Nested
    @DisplayName("예약 API 응답 테스트")
    class ApiResponseTest {

        @Test
        @DisplayName("기본 URI로 요청 시 정상적으로 어드민 페이지가 반환된다.")
        void shouldReturnAdminPage_whenDefaultURI() {
            RestAssured.given().log().all()
                    .when().get("/")
                    .then().log().all()
                    .statusCode(200);
        }

        @Test
        @DisplayName("예약 관리 페이지 URI로 요청 시 정상적으로 관리 페이지가 반환된다.")
        void shouldReturnReservationPage() {
            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200);
        }

        @Test
        @DisplayName("예약 시 저장된 시간으로 입력했을 경우 정상적으로 추가된다.")
        void shouldReserve_whenValidTime() {
            Map<String, String> time = new HashMap<>();
            time.put("time", "10:00");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(time)
                    .when().post("/times")
                    .then().log().all()
                    .statusCode(201);

            Map<String, String> reservation = new HashMap<>();
            reservation.put("name", "브라운");
            reservation.put("date", "2030-08-05");
            reservation.put("timeId", "1");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(reservation)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201)
                    .header("Location", "/reservations/1");
        }

        @Test
        @DisplayName("예약 추가할 때 저장된 시간이 아닌 경우 예외가 발생한다.")
        void shouldThrowException_whenInvalidTime() {
            Map<String, String> reservation = new HashMap<>();
            reservation.put("name", "브라운");
            reservation.put("date", "2030-08-05");
            reservation.put("timeId", "1");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(reservation)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400)
                    .body(containsString("존재하지 않는 시간이에요."));
        }

        @Test
        @DisplayName("예약 추가할 때 이미 지난 날짜이거나 시간일 경우 예외가 발생한다.")
        void shouldThrowException_whenPastedDateOrTime() {
            Map<String, String> time = new HashMap<>();
            time.put("time", "10:00");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(time)
                    .when().post("/times")
                    .then().log().all()
                    .statusCode(201);

            Map<String, String> reservation = new HashMap<>();
            reservation.put("name", "브라운");
            reservation.put("date", "2025-06-30");
            reservation.put("timeId", "1");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(reservation)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400)
                    .body(containsString("이미 지난 날짜 및 시간은 예약할 수 없어요."));
        }

        @Test
        @DisplayName("예약 삭제가 정상적으로 이루어진다.")
        void shouldDeleteReservation() {
            Map<String, String> time = new HashMap<>();
            time.put("time", "10:00");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(time)
                    .when().post("/times")
                    .then().log().all()
                    .statusCode(201);

            Map<String, String> reservation = new HashMap<>();
            reservation.put("name", "브라운");
            reservation.put("date", "2030-08-05");
            reservation.put("timeId", "1");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(reservation)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201);

            RestAssured.given().log().all()
                    .when().delete("/reservations/1")
                    .then().log().all()
                    .statusCode(204);
        }

        @Test
        @DisplayName("삭제할 예약이 없는 경우 예외가 발생한다.")
        void shouldThrowException_whenNotFoundReservation() {
            RestAssured.given().log().all()
                    .when().delete("/reservations/1")
                    .then().log().all()
                    .statusCode(400)
                    .body(containsString("존재하지 않는 예약이에요."));
        }
    }

    @Nested
    @DisplayName("예약 API 데이터베이스 연동 테스트")
    class ReservationApiWithDatabaseTest {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Test
        @DisplayName("정상적으로 데이터베이스가 연결되어 예약 테이블이 생성된다.")
        void shouldCreateReservationTable() {
            try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
                assertThat(connection).isNotNull();
                assertThat(connection.getCatalog()).isEqualTo("DATABASE");
                assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        @DisplayName("예약 테이블에 예약을 추가한 후 정상적으로 조회가 된다.")
        void shouldReturnDatabaseCountRow_whenAddReservation() {
            String timeSql = "INSERT INTO time (time) VALUES (?)";
            String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
            jdbcTemplate.update(timeSql, "10:00");
            jdbcTemplate.update(sql, "브라운", "2030-08-05", "1");

            List<ResponseReservation> reservations = RestAssured
                    .given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200).extract()
                    .jsonPath().getList(".", ResponseReservation.class);

            String countSql = "SELECT count(1) from reservation";
            Integer count = jdbcTemplate.queryForObject(countSql, Integer.class);

            assertThat(reservations.size()).isEqualTo(count);
        }

        @Test
        @DisplayName("예약 API를 이용하여 예약을 추가한 후 정상적으로 데이터베이스에서 조회가 된다.")
        void shouldReservationListInDatabase_whenUsingReservationAPI() {
            Map<String, String> time = new HashMap<>();
            time.put("time", "10:00");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(time)
                    .when().post("/times")
                    .then().log().all()
                    .statusCode(201);

            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", "2027-08-05");
            params.put("timeId", "1");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201)
                    .header("Location", "/reservations/1");

            Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
            assertThat(count).isEqualTo(1);
        }

        @Test
        @DisplayName("예약 API를 이용하여 예약을 취소하면 정상적으로 데이터베이스에서 삭제가 된다.")
        void shouldDeleteReservationInDatabase_whenUsingReservationAPI() {
            Map<String, String> time = new HashMap<>();
            time.put("time", "10:00");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(time)
                    .when().post("/times")
                    .then().log().all()
                    .statusCode(201);

            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", "2027-08-05");
            params.put("timeId", "1");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201)
                    .header("Location", "/reservations/1");

            RestAssured.given().log().all()
                    .when().delete("/reservations/1")
                    .then().log().all()
                    .statusCode(204);

            Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
            assertThat(countAfterDelete).isEqualTo(0);
        }
    }

    @Nested
    @DisplayName("시간 API 응답 및 데이터베이스 연동 테스트")
    class TimeApiWithDatabaseTest {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Test
        @DisplayName("정상적으로 데이터베이스가 연결되어 예약 테이블이 생성된다.")
        void shouldCreateTimeTable() {
            try (Connection connection = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
                assertThat(connection).isNotNull();
                assertThat(connection.getCatalog()).isEqualTo("DATABASE");
                assertThat(connection.getMetaData().getTables(null, null, "TIME", null).next()).isTrue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        @DisplayName("시간 추가 API를 이용하여 추가가 정상적으로 이루어진다.")
        void shouldCreateTime() {
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
        }

        @Test
        @DisplayName("추가한 시간이 정상적으로 삭제된다.")
        void shouldDeleteTime() {
            Map<String, String> params = new HashMap<>();
            params.put("time", "10:00");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/times")
                    .then().log().all()
                    .statusCode(201);

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
    }

    @Nested
    @DisplayName("아키텍처 분리 테스트")
    class ArchitectureTest {

        @Autowired
        private ReservationController reservationController;

        @Test
        @DisplayName("레이어드 아키텍처 적용이 정상적으로 이루어져있는지 확인한다.")
        void shouldCheckedLayeredArchitecture() {
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
}
