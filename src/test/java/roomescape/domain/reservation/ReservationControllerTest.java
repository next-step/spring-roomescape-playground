package roomescape.domain.reservation;

import static io.restassured.http.ContentType.JSON;

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
                .statusCode(200);
        //  .body("size()", is(3)); // 아직 생성 요청이 없으니 Controller에서 임의로 넣어준 Reservation 갯수 만큼 검증하거나 0개임을 확인하세요.
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
                    .header("Location", "/reservations/1");
            //   .body("id", is(1));
        }
    }
}
