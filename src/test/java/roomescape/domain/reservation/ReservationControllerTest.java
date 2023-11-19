package roomescape.domain.reservation;

import static org.hamcrest.Matchers.is;
import static roomescape.helper.TestHelper.deleteMethodTest;
import static roomescape.helper.TestHelper.getMethodTest;
import static roomescape.helper.TestHelper.postMethodTest;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import roomescape.RoomescapeApplicationTest;
import roomescape.domain.reservation.dao.ReservationRepository;
import roomescape.domain.reservation.dao.SimpleReservationRepository;

public class ReservationControllerTest extends RoomescapeApplicationTest {

    private final ReservationRepository reservationRepository = new SimpleReservationRepository();

    @Test
    @DisplayName("reservation 페이지 조회 시 200 OK를 반환한다.")
    public void reservation_페이지_조회_시_200_OK를_반환한다() {
        getMethodTest("/reservation", 200);
    }

    @Test
    @DisplayName("예약 리스트 조회 시 200 OK를 반환한다.")
    public void 예약_리스트_조회_시_200_OK를_반환한다() {
        getMethodTest("/reservations", 200)
                .body("size()", is(0));
    }

    @Nested
    @DisplayName("예약 추가 테스트")
    class CreateReservationTest {

        private Map<String, String> params;

        @BeforeEach
        void setUp() {
            params = putAllParams("브라운", "2023-08-05", "15:40");
            reservationRepository.clear();
        }

        @Test
        @DisplayName("예약 추가 시 201 OK를 반환한다.")
        public void 예약_추가_시_201_OK를_반환한다() {
            postMethodTest(params, "/reservations", 201)
                    .header("Location", "/reservations/1")
                    .body("id", is(1));
        }

        @Test
        @DisplayName("예약 추가 시 예약 리스트의 크기는 증가한다.")
        public void 예약_추가_시_예약_리스트의_크기는_증가한다() {
            // given
            getMethodTest("/reservations", 200)
                    .body("size()", is(0));

            // when
            postMethodTest(params, "/reservations", 201)
                    .header("Location", "/reservations/1")
                    .body("id", is(1));

            // then
            getMethodTest("/reservations", 200)
                    .body("size()", is(1));
        }

        @Test
        @DisplayName("필요한 인자가 없는 경우 예약 추가는 400 Bad Request를 반환한다.")
        public void 필요한_인자가_없는_경우_예약_추가는_400_Bad_Request를_반환한다() {
            // given
            params = putAllParams("브라운", "", "");

            // when & then
            postMethodTest(params, "/reservations", 400);
        }
    }

    @Nested
    @DisplayName("예약 취소 테스트")
    class DeleteReservationTest {

        private Map<String, String> params;

        @BeforeEach
        void setUp() {
            params = putAllParams("브라운", "2023-08-05", "15:40");
            reservationRepository.clear();
        }

        @Test
        @DisplayName("예약 취소는 성공한다.")
        public void 예약_취소는_성공한다() {
            // given
            postMethodTest(params, "/reservations", 201)
                    .header("Location", "/reservations/1")
                    .body("id", is(1));

            // when & then
            deleteMethodTest("/reservations", 1, 204);
        }

        @Test
        @DisplayName("예약 취소 시 예약 리스트의 크기는 감소한다.")
        public void 예약_취소_시_예약_리스트의_크기는_감소한다() {
            // given
            postMethodTest(params, "/reservations", 201)
                    .header("Location", "/reservations/1")
                    .body("id", is(1));

            getMethodTest("/reservations", 200)
                    .body("size()", is(1));

            //when
            deleteMethodTest("/reservations", 1, 204);

            //then
            getMethodTest("/reservations", 200)
                    .body("size()", is(0));
        }

        @Test
        @DisplayName("삭제할 예약을 찾을 수 없을 경우 400 Bad Request를 반환한다.")
        public void 삭제할_예약을_찾을_수_없을_경우_400_Bad_Request를_반환한다() {
            deleteMethodTest("/reservations", -1, 400);
            deleteMethodTest("/reservations", 0, 400);
        }
    }

    private Map<String, String> putAllParams(String name, String date, String time) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("date", date);
        params.put("time", time);
        return params;
    }
}
