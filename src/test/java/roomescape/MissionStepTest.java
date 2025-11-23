package roomescape;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;
import static org.assertj.core.api.Assertions.assertThat;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

//    @Test
//    void 일단계() {
//        RestAssured.given().log().all()
//                .when().get("/")
//                .then().log().all()
//                .statusCode(200);
//    }

//    @Test
//    void 이단계() {
//        RestAssured.given().log().all()
//                .when().get("/reservation")
//                .then().log().all()
//                .statusCode(200);
//
//        RestAssured.given().log().all()
//                .when().get("/reservations")
//                .then().log().all()
//                .statusCode(200)
//                .body("size()", is(3)); // 아직 생성 요청이 없으니 Controller에서 임의로 넣어준 Reservation 갯수 만큼 검증하거나 0개임을 확인하세요.
//    }
//


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 칠단계() {
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

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }


    @Test
    void 칠단계_실패_테스트_이름누락() {

        Map<String, String> params = new HashMap<>();
        params.put("date", "2023-08-05");
        params.put("time", "10:00");

        RestAssured.given().contentType(ContentType.JSON).body(params)
                .when().post("/reservations")
                .then()
                .statusCode(400);
    }
}
