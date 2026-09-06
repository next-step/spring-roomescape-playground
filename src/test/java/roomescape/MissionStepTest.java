package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void returnBadRequestWhenReservationRequestIsInvalid() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "",
                            "date": "",
                            "time": ""
                        }
                        """)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("message", equalTo("예약 정보가 올바르지 않습니다."));
    }

    @Test
    void returnNotFoundWhenReservationDoesNotExist() {
        RestAssured.given().log().all()
                .when().delete("/reservations/999999")
                .then().log().all()
                .statusCode(404)
                .body("message", equalTo("예약을 찾을 수 없습니다."));
    }

    @Test
    void 오단계() {
        try (Connection connection =
                     jdbcTemplate.getDataSource().getConnection()) {

            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");

            assertThat(
                    connection.getMetaData()
                            .getTables(null, null, "RESERVATION", null)
                            .next()
            ).isTrue();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void sevenStep() {
        Map<String, String> params = new HashMap<>();

        params.put("name", "브라운");
        params.put(
                "date",
                java.time.LocalDate.now(java.time.ZoneId.of("Asia/Seoul"))
                        .plusDays(1)
                        .toString()
        );
        params.put("time", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1");

        Integer count = jdbcTemplate.queryForObject(
                "SELECT count(1) FROM reservation",
                Integer.class
        );

        assertThat(count).isEqualTo(1);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject(
                "SELECT count(1) FROM reservation",
                Integer.class
        );

        assertThat(countAfterDelete).isEqualTo(0);
    }
}