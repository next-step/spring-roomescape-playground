package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import roomescape.dto.Reservation;
import roomescape.exception.ReservationInvalidException;

class ReservationControllerTest {
    private final ReservationController controller = new ReservationController(
            Clock.fixed(Instant.parse("2030-08-05T15:40:00Z"), ZoneOffset.UTC));

    @Test
    void 현재_시간보다_이전이면_예약할_수_없다() {
        Reservation reservation = new Reservation(
                null, "브라운", LocalDate.of(2030, 8, 5), LocalTime.of(15, 39));

        assertThatThrownBy(() -> controller.createReservation(reservation))
                .isInstanceOf(ReservationInvalidException.class)
                .hasMessage("과거 시간으로 예약할 수 없습니다");
    }

    @Test
    void 현재_시간이면_예약할_수_있다() {
        Reservation reservation = new Reservation(
                null, "브라운", LocalDate.of(2030, 8, 5), LocalTime.of(15, 40));

        assertThat(controller.createReservation(reservation).getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
