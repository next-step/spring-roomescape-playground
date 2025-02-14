package roomescape.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.response.ReservationResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationServiceTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 총_예약건수를_조회할_수_있다() {
        // given
        jdbcTemplate.update("INSERT INTO reservation (customer_name, reservation_date, reservation_time) VALUES (?, ?, ?)", "예약생성못해쿼리투입", "2025-02-12", "15:00");
        // when
        List<ReservationResponse> reservations = reservationService.getReservations();
        // then
        assertThat(reservations.size() == 1).isTrue();
    }

//    @Test
//    void 예약을_삭제할_수_있다() {
//        // given
//        CreateReservationRequest request = new CreateReservationRequest(
//                "김철수",
//                LocalDate.of(2025, 2, 6),
//                LocalTime.of(12, 30));
//        reservationService.createReservation(request);
//        // when & then
//        assertThatCode(() -> reservationService.deleteReservation(1))
//                .doesNotThrowAnyException();
//    }
//
//    @Test
//    void 존재하지_않는_예약을_삭제하는_경우_오류_발생() {
//        assertThatThrownBy(() -> reservationService.deleteReservation(1))
//                .isInstanceOf(BadRequestException.class)
//                .hasMessageContaining(
//                        ExceptionMessage.RESERVATION_NOT_EXISTS.getMessage());
//    }
}
