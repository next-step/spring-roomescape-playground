package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:time-controller-test"
)
public class TimeControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @Sql(
            scripts = "/time-test-data.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void 시간_목록을_조회할_수_있다() {
        given()
                .when()
                .get("/times")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", is(1))
                .body("[0].time", is("10:00"));
    }

    @Test
    @Sql(
            statements = {
                    "DELETE FROM reservation",
                    "DELETE FROM time",
                    "ALTER TABLE time ALTER COLUMN id RESTART WITH 1"
            },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void 시간을_추가할_수_있다() {
        Map<String, String> params = new HashMap<>();
        params.put("time", "11:00");

        given()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("/times")
                .then()
                .statusCode(201)
                .header("Location", "/times/1")
                .body("id", is(1))
                .body("time", is("11:00"));
    }

    @Test
    @Sql(
            scripts = "/time-test-data.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void 시간을_삭제할_수_있다() {
        given()
                .when()
                .delete("times/1")
                .then()
                .statusCode(204);
    }
}
