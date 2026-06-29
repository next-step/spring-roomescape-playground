package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ApiController;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApiController apiController;

    private void initTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS time");
        jdbcTemplate.execute("DROP TABLE IF EXISTS member");

        jdbcTemplate.execute("CREATE TABLE time (id BIGINT NOT NULL AUTO_INCREMENT, time TIME NOT NULL, PRIMARY KEY (id))");
        jdbcTemplate.execute("CREATE TABLE member (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL, email VARCHAR(255) NOT NULL UNIQUE, password VARCHAR(255) NOT NULL, role VARCHAR(255) NOT NULL, PRIMARY KEY (id))");
        jdbcTemplate.execute("CREATE TABLE reservation (id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255) NOT NULL, date DATE NOT NULL, time_id BIGINT, theme_id BIGINT, PRIMARY KEY (id), FOREIGN KEY (time_id) REFERENCES time(id))");

        jdbcTemplate.execute("INSERT INTO member (name, email, password, role) VALUES ('어드민', 'admin@email.com', 'password', 'ADMIN')");
    }

    @Test
    void 오단계() {
        initTable();
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog().toUpperCase()).isEqualTo("DATABASE");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 팔단계() {
        initTable();
        Map<String, String> params = new HashMap<>();
        params.put("time", "10:00:00");

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
    void 구단계() {
        initTable();
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
    void 십단계() {
        boolean isJdbcTemplateInjected = false;
        for (Field field : apiController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }
        assertThat(isJdbcTemplateInjected).isFalse();
    }

    @Test
    void 일단계() {
        initTable();
        Map<String, String> params = new HashMap<>();
        params.put("email", "admin@email.com");
        params.put("password", "password");

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/login")
                .then().log().all()
                .statusCode(200)
                .extract();

        String token = response.headers().get("Set-Cookie").getValue().split(";")[0].split("=")[1];
        assertThat(token).isNotBlank();

        ExtractableResponse<Response> checkResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .cookie("token", token)
                .when().get("/login/check")
                .then().log().all()
                .statusCode(200)
                .extract();

        assertThat(checkResponse.body().jsonPath().getString("name")).isEqualTo("어드민");
    }

    @Test
    void 이단계() {
        initTable();
        Map<String, String> loginParams = new HashMap<>();
        loginParams.put("email", "admin@email.com");
        loginParams.put("password", "password");

        ExtractableResponse<Response> loginResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(loginParams)
                .when().post("/login")
                .then().statusCode(200)
                .extract();

        String token = loginResponse.headers().get("Set-Cookie").getValue().split(";")[0].split("=")[1];

        Map<String, String> timeParam = new HashMap<>();
        timeParam.put("time", "10:00:00");
        RestAssured.given().contentType(ContentType.JSON).body(timeParam).post("/times");


        Map<String, String> params = new HashMap<>();
        params.put("date", "2024-03-01");
        params.put("time", "1");
        params.put("theme", "1");

        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .body(params)
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .post("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.jsonPath().getString("name")).isEqualTo("어드민");

        params.put("name", "브라운");
        params.put("date", "2024-03-02");

        ExtractableResponse<Response> adminResponse = RestAssured.given().log().all()
                .body(params)
                .cookie("token", token)
                .contentType(ContentType.JSON)
                .post("/reservations")
                .then().log().all()
                .extract();

        assertThat(adminResponse.statusCode()).isEqualTo(201);
        assertThat(adminResponse.jsonPath().getString("name")).isEqualTo("브라운");
    }
}
