package roomescape.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.IntegrationTestSupport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationIntegrationTest extends IntegrationTestSupport {
    private static final LocalDate DATE = LocalDate.of(2027, 8, 5);
    private static final LocalTime TIME = LocalTime.of(15, 40);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("delete from reservations where time_id in (select id from times where start_at = ?)", TIME);
        jdbcTemplate.update("delete from times where start_at = ?", TIME);
    }

    @Test
    void 조회_API_결과와_DB_조회_결과가_일치한다() {
        // given
        Long timeId = insertTime();
        jdbcTemplate.update("insert into reservations (name, reservation_date, time_id) values (?, ?, ?)",
                "브라운", DATE, timeId);

        // when
        int apiCount = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().statusCode(200)
                .extract().jsonPath().getList("reservations").size();

        // then
        Integer dbCount = jdbcTemplate.queryForObject("select count(*) from reservations", Integer.class);
        assertThat(apiCount).isEqualTo(dbCount);
    }

    @Test
    void 추가_API_요청_후_DB에_데이터가_저장된다() {
        // given
        Long timeId = insertTime();
        Map<String, Object> request = new HashMap<>();
        request.put("name", "브라운");
        request.put("date", DATE.toString());
        request.put("timeId", timeId);

        // when
        Long id = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().statusCode(201)
                .extract().jsonPath().getLong("id");

        // then
        Integer dbCount = jdbcTemplate.queryForObject(
                "select count(*) from reservations where id = ?", Integer.class, id);
        assertThat(dbCount).isEqualTo(1);
    }

    @Test
    void 기존_예약_추가_API_형식으로_요청하면_400을_반환한다() {
        // given
        Map<String, String> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("time", "10:00");

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    void 삭제_API_요청_후_DB에_데이터가_삭제된다() {
        // given
        Long timeId = insertTime();
        jdbcTemplate.update("insert into reservations (name, reservation_date, time_id) values (?, ?, ?)",
                "브라운", DATE, timeId);
        Long id = jdbcTemplate.queryForObject("select id from reservations where reservation_date = ? and time_id = ?",
                Long.class, DATE, timeId);

        // when
        RestAssured.given().log().all()
                .when().delete("/reservations/" + id)
                .then().statusCode(204);

        // then
        Integer dbCount = jdbcTemplate.queryForObject(
                "select count(*) from reservations where id = ?", Integer.class, id);
        assertThat(dbCount).isZero();
    }

    private Long insertTime() {
        jdbcTemplate.update("insert into times (start_at) values (?)", TIME);
        return jdbcTemplate.queryForObject("select id from times where start_at = ?", Long.class, TIME);
    }
}
