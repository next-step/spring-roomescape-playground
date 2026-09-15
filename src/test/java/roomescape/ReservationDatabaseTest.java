package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;


@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationDatabaseTest {
    private LocalDate testDate = LocalDate.now().plusDays(1);

    @LocalServerPort
    int port;


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("DB에 예약 데이터가 존재할 때 예약 목록 조회 API 호출 시 정상 반환된다")
    void testReadReservationsFromDatabase() {
        RestAssured.port = this.port;
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "15:40");
        Long timeId = jdbcTemplate.queryForObject("SELECT id FROM time WHERE time = '15:40'", Long.class);

        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2026-10-05", timeId);
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(count))
                .body("time.first().id", is(timeId.intValue()))
                .body("time.first().time", is("15:40"));
    }

    @Test
    @DisplayName("예약 추가, 취소 API가 정상 작동하여 DB에 반영된다")
    void testUpdateReservationsFromDatabase() {
        RestAssured.port = this.port;
        jdbcTemplate.update("INSERT INTO time (time) VALUES (?)", "10:00");
        Long timeId = jdbcTemplate.queryForObject("SELECT id FROM time WHERE time = '10:00'", Long.class);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", testDate.toString());
        params.put("time", timeId);

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
        assertThat(countAfterDelete).isEqualTo(0);
    }
}
