package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.entity.Reservation;
import roomescape.entity.Time;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Nested
    public class SpringMvcTest {

        @Test
        @DisplayName("1단계 홈 화면 불러오기")
        void step1() {
            RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
        }

        @Test
        @DisplayName("2단계 예약 목록 조회하기")
        void step2() {
            RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);

            RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0)); // 아직 생성 요청이 없으니 Controller에서 임의로 넣어준 Reservation 갯수 만큼 검증하거나 0개임을 확인하세요.
        }

        @Test
        @DisplayName("3단계 예약 목록 추가 및 삭제하기")
        void step3() {
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

        @Test
        @DisplayName("4단계 예약 목록 추가 및 삭제시 예외처리")
        void step4() {
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
                .statusCode(400);
        }
    }



    @Nested
    public class SpringJdbcTest {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Test
        @DisplayName("5단계 JdbcTemplate을 이용하여 h2 데이터베이스 연동하기")
        void step5() {
            try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
                assertThat(connection).isNotNull();
                assertThat(connection.getCatalog()).isEqualTo("database");
                assertThat(connection.getMetaData().getTables(null, null, "reservation", null).next()).isTrue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        @DisplayName("6단계 데이터베이스 조회하기")
        void step6() {
            jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");

            List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

            Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

            assertThat(reservations.size()).isEqualTo(count);
        }

        @Test
        @DisplayName("7단계 데이터베이스 추가 및 삭제하기")
        void step7() {
            Time time = Time.create(1L, "10:00");
            Reservation reservation = Reservation.create(1L, "브라운", "2023-08-05", time);

            RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
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
    }

    @Nested
    public class SpringCoreTest {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Test
        @DisplayName("8단계 시간 추가, 삭제 및 조회하기")
        void step8() {
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

        @Test
        @DisplayName("9단계 기존 방식의 예약 추가 실패")
        void step9() {
            Map<String, String> reservation = new HashMap<>();
            reservation.put("name", "브라운");
            reservation.put("date", "2023-08-05");
            reservation.put("time", "10:00");

            RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
        }

        @Autowired
        private ReservationController reservationController;

        @Test
        @DisplayName("10단계 계층화 리팩터링")
        void step10() {
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
