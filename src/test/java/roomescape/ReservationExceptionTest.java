package roomescape;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationExceptionTest {
    @Test
    @DisplayName("필요한 인자가 없는 경우 예외가 발생한다")
    void createException() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(createParams())
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("삭제할 예약이 없는 경우 예외가 발생한다")
    void deleteException() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(404);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("DB에 접근이 실패할 경우 예외가 발생한다.")
    void runtimeExeptionTest() {
        jdbcTemplate.execute("DROP TABLE reservation");

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(500);
    }

    private Map<String, String> createParams() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "");
        params.put("time", "");

        return params;
    }
}
