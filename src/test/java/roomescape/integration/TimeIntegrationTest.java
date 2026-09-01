package roomescape.integration;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.IntegrationTestSupport;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

class TimeIntegrationTest extends IntegrationTestSupport {
    private static final LocalTime TIME = LocalTime.of(10, 0);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 조회_API_결과와_DB_조회_결과가_일치한다() {
        // given
        jdbcTemplate.update("insert into times (start_at) values (?)", TIME);

        // when
        int apiCount = RestAssured.given().log().all()
                .when().get("/times")
                .then().statusCode(200)
                .extract().jsonPath().getList("times").size();

        // then
        Integer dbCount = jdbcTemplate.queryForObject("select count(*) from times", Integer.class);
        assertThat(apiCount).isEqualTo(dbCount);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.update("delete from times where start_at = ?", TIME);
    }

    @Test
    void 추가_API_요청_후_DB에_데이터가_저장되고_생성된_시간대를_반환한다() {
        // given
        Map<String, String> request = createTimeRequest(TIME.toString());

        // when
        JsonPath response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().statusCode(201)
                .extract().jsonPath();

        // then
        Long id = response.getLong("id");
        assertThat(id).isNotNull();
        assertThat(response.getString("time")).isEqualTo(TIME.toString());

        Integer dbCount = jdbcTemplate.queryForObject(
                "select count(*) from times where id = ?", Integer.class, id);
        assertThat(dbCount).isEqualTo(1);

        LocalTime savedTime = jdbcTemplate.queryForObject(
                "select start_at from times where id = ?", LocalTime.class, id);
        assertThat(savedTime).isEqualTo(TIME);
    }

    @Test
    void 중복된_시간대_추가_API_요청_시_409를_반환한다() {
        // given
        Map<String, String> request = createTimeRequest(TIME.toString());

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().statusCode(201);

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then()
                .statusCode(409)
                .body("code", equalTo("TIME_CONFLICT"))
                .body("message", equalTo("해당 예약 시간대가 이미 존재합니다."));
    }

    private Map<String, String> createTimeRequest(String time) {
        Map<String, String> request = new HashMap<>();
        request.put("time", time);
        return request;
    }
}
