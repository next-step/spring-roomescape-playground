package roomescape;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
import roomescape.domain.Reservation;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

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
    @DisplayName("예약 추가가 정상적으로 이루어진다.")
    void shouldCreateReservation() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2030-08-05");
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
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);
    }

    @Test
    @DisplayName("기존 예약을 정상적으로 취소한다.")
    void shouldCancelReservation() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2030-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약하기 위한 데이터가 정상적으로 입력되지 않았을 경우 예외가 발생한다.")
    void shouldThrowException_whenInvalidReservationInputData() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body(containsString("예약하기 위한 데이터(이름, 날짜, 시간)를 모두 입력해 주세요."));
    }

    @Test
    @DisplayName("예약 날짜의 형식이 올바르지 않을 경우 예외가 발생한다.")
    void shouldThrowException_whenInvalidFormatReservationDateAndTime() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2025-13-33");
        params.put("time", "13:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then()
                .statusCode(400)
                .body(containsString("날짜(년도-월-일)형식에 맞게 입력해 주세요. ex) 2020-12-31"));
    }

    @Test
    @DisplayName("삭제할 예약이 없는 경우 예외가 발생한다.")
    void shouldThrowException_whenNotFoundReservation() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(400)
                .body(containsString("예약 ID가 존재하지 않아요."));
    }

    @Nested
    @DisplayName("데이터베이스 테스트")
    class DatabaseTest {

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
            String sql = "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, "브라운", "2030-08-05", "15:40");

            List<Reservation> reservations = RestAssured
                    .given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200).extract()
                    .jsonPath().getList(".", Reservation.class);

            String countSql = "SELECT count(1) from reservation";
            Integer count = jdbcTemplate.queryForObject(countSql, Integer.class);

            assertThat(reservations.size()).isEqualTo(count);
        }

        @Test
        @DisplayName("예약 API를 이용하여 예약을 추가한 후 정상적으로 데이터베이스에서 조회가 된다.")
        void shouldReservationListInDatabase_whenUsingReservationAPI() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", "2027-08-05");
            params.put("time", "10:00");

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
            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", "2027-08-05");
            params.put("time", "10:00");

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
}
