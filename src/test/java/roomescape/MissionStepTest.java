package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationRepository;

@SuppressWarnings("NonAsciiCharacters")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Nested
    class 이단계 {
        @Test
        void 예약_페이지_요청() {
            RestAssured.given().log().all()
                    .when().get("/reservation")
                    .then().log().all()
                    .statusCode(200);
        }

        @Test
        void 예약_목록_요청() {
            // given
            new Reservation(
                    1L,
                    "브라운",
                    LocalDateTime.now()
            );

            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(0));
        }
    }
}
