package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SuppressWarnings("NonAsciiCharacters")
class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("어드민 메인 페이지를 반환한다")
    void 일단계() {
        RestAssured.given().log().all()
                   .when().get("/")
                   .then().log().all()
                   .statusCode(200);
    }

    @Test
    @DisplayName("예약 관리 페이지를 반환한다")
    void 이단계_1() {
        RestAssured.given().log().all()
                   .when().get("/reservation")
                   .then().log().all()
                   .statusCode(200);
    }

    @Test
    @DisplayName("예약 목록을 조회한다")
    void 이단계_2() {
        RestAssured.given().log().all()
                   .when().get("/reservations")
                   .then().log().all()
                   .statusCode(200)
                   .body("size()", is((0)));
    }

    @Test
    @DisplayName("예약을 추가한다")
    void 삼단계_1() {
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
    }

    @Test
    @DisplayName("예약을 삭제한다")
    @Disabled
    void 삼단계_2() {
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
    @DisplayName("예약 추가 시 필요한 인자가 없는 경우 Status Code가 400이다")
    void 사단계_1() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        RestAssured.given().log().all()
                   .contentType(ContentType.JSON)
                   .body(params)
                   .when().post("/reservations")
                   .then().log().all()
                   .statusCode(400);
    }

    @Test
    @DisplayName("예약 삭제 시 삭제할 예약이 없는 경우 Status Code가 400이다")
    void 사단계_2() {
        RestAssured.given().log().all()
                   .when().delete("/reservations/1")
                   .then().log().all()
                   .statusCode(400);
    }

    @Test
    @DisplayName("데이터베이스를 적용한다")
    void 오단계() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용한다")
    void 육단계() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");

        List<Reservation> reservations = RestAssured.given().log().all()
                                                    .when().get("/reservations")
                                                    .then().log().all()
                                                    .statusCode(200).extract()
                                                    .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations).hasSize(count);
    }

    @Test
    @DisplayName("예약 추가 API 처리 로직에서 데이터베이스를 활용한다")
    void 칠단계_1() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
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
}
