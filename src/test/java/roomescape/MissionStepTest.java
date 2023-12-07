package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MissionStepTest {

    @Autowired
    private ReservationController reservationController;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TimeDao timeDao;

    @Autowired
    private ReservationDao reservationDao;

    @Test
    void 홈_화면을_반환한다() {
        RestAssured.given().log().all()
            .when().get("/")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    void 에약_정보를_반환한다() {
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
    void 예약_정보를_추가_취소한다() {
        Time time = new Time(null, LocalTime.of(15, 40));
        Time savedTime = timeDao.save(time);
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", savedTime.getId());

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
    void 잘못된_요청인_경우_예외가_발생한다() {
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

    @Test
    void 데이터베이스_연결이_정상적으로_수행된다() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 데이터베이스에_저장된_예약의_개수와_예약조회결과의_개수가_같다() {
        Time time = timeDao.save(Time.from(LocalTime.of(15, 40)));
        Reservation reservation = reservationDao.save(Reservation.of("브라운", LocalDate.of(2023, 8, 5), time));

        List<Reservation> reservations = RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200).extract()
            .jsonPath().getList(".");

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations).hasSize(count);
    }

    @Test
    void 예약_추가_취소과정에_DB를_활용한다() {
        Time time = new Time(null, LocalTime.of(15, 40));
        Time savedTime = timeDao.save(time);
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", savedTime.getId());

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

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isZero();
    }

    @Test
    void 시간_정보를_추가_수정_삭제한다() {
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
    void 잘못된_정보로_예약을_수행한다() {
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

    @Test
    void Controller에_jdbcTemplate이_존재하지_않는다() {
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
