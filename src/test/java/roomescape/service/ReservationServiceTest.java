package roomescape.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.dto.request.CreateReservationRequest;
import roomescape.global.exception.BadRequestException;
import roomescape.global.exception.ExceptionMessage;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationServiceTest {

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {
        reservationService = new ReservationService();
    }

    @Test
    void 예약을_삭제할_수_있다() {
        // given
        CreateReservationRequest request = new CreateReservationRequest(
                "김철수",
                LocalDate.of(2025, 2, 6),
                LocalTime.of(12, 30));
        reservationService.createReservation(request);
        // when & then
        assertThatCode(() -> reservationService.deleteReservation(1))
                .doesNotThrowAnyException();
    }

    @Test
    void 존재하지_않는_예약을_삭제하는_경우_오류_발생() {
        assertThatThrownBy(() -> reservationService.deleteReservation(1))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining(
                        ExceptionMessage.RESERVATION_NOT_EXISTS.getMessage());
    }
}
