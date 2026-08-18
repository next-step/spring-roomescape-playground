package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationIntegrationTest {
    private static final LocalDate DATE = LocalDate.of(2027, 8, 5);
    private static final LocalTime TIME = LocalTime.of(15, 40);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("delete from reservations where reservation_date = ? and reservation_time = ?",
                DATE, TIME);
    }

    @Test
    void 조회_API_결과와_DB_조회_결과가_일치한다() {
        // given
        jdbcTemplate.update("insert into reservations (name, reservation_date, reservation_time) values (?, ?, ?)",
                "브라운", DATE, TIME);

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
        Map<String, String> request = new HashMap<>();
        request.put("name", "브라운");
        request.put("date", DATE.toString());
        request.put("time", TIME.toString());

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
    void 삭제_API_요청_후_DB에_데이터가_삭제된다() {
        // given
        jdbcTemplate.update("insert into reservations (name, reservation_date, reservation_time) values (?, ?, ?)",
                "브라운", DATE, TIME);
        Long id = jdbcTemplate.queryForObject(
                "select id from reservations where reservation_date = ? and reservation_time = ?",
                Long.class, DATE, TIME);

        // when
        RestAssured.given().log().all()
                .when().delete("/reservations/" + id)
                .then().statusCode(204);

        // then
        Integer dbCount = jdbcTemplate.queryForObject(
                "select count(*) from reservations where id = ?", Integer.class, id);
        assertThat(dbCount).isZero();
    }
}
