package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.presentation.ReservationController;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CoreStepTest {

    @Autowired
    private ReservationController reservationController;

    @Test
    void 팔단계() {
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
    void 구단계() {
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

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }
}
