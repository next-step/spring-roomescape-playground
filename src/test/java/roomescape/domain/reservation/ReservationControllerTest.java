package roomescape.domain.reservation;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.RoomescapeApplicationTest;

public class ReservationControllerTest extends RoomescapeApplicationTest {

    @Test
    @DisplayName("reservation 페이지 조회 시 200 OK를 반환한다.")
    public void reservation_페이지_조회_시_200_OK를_반환한다() {
        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 리스트 조회 시 200 OK를 반환한다.")
    public void 예약_리스트_조회_시_200_OK를_반환한다() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Nested
    @DisplayName("예약 추가 테스트")
    class CreateReservationTest {

        private final Map<String, String> params = new HashMap<>();

        @BeforeEach
        void setUp() {
            params.put("name", "브라운");
            params.put("date", "2023-08-05");
            params.put("time", "15:40");
        }

        @Test
        @DisplayName("예약 추가 시 201 OK를 반환한다.")
        public void 예약_추가_시_201_OK를_반환한다() {
            RestAssured.given().log().all()
                    .contentType(JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201)
                    .header("Location", "/reservations/1")
                    .body("id", is(1));
        }

        @Test
        @DisplayName("예약 추가 시 예약 리스트의 크기는 증가한다.")
        public void 예약_추가_시_예약_리스트의_크기는_증가한다() {
            // given & when
            RestAssured.given().log().all()
                    .contentType(JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201)
                    .header("Location", "/reservations/1")
                    .body("id", is(1));

            // then
            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(1));
        }
    }

    @Nested
    @DisplayName("예약 취소 테스트")
    class DeleteReservationTest {

        @BeforeEach
        void setUp() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", "2023-08-05");
            params.put("time", "15:40");
            RestAssured.given().log().all()
                    .contentType(JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201)
                    .header("Location", "/reservations/1")
                    .body("id", is(1));
        }

        @Test
        @DisplayName("예약 취소는 성공한다.")
        public void 예약_취소는_성공한다() {
            RestAssured.given().log().all()
                    .when().delete("/reservations/1")
                    .then().log().all()
                    .statusCode(204);
        }

        @Test
        @DisplayName("예약 취소 시 예약 리스트의 크기는 감소한다.")
        public void 예약_취소_시_예약_리스트의_크기는_감소한다() {
            //given
            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(1));

            //when
            RestAssured.given().log().all()
                    .when().delete("/reservations/1")
                    .then().log().all()
                    .statusCode(204);

            //then
            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(0));
        }
    }
}
