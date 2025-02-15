package roomescape.domain.reservation;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class ReservationTest {

    private final ReservedDateTime reservedDateTime = new ReservedDateTime(LocalDate.now(), LocalTime.now());

    @Test
    void id값이_같은_예약객체는_동일_테스트() {
        // given
        Long id = 1L;
        Reservation reservation = new Reservation(id, "test", reservedDateTime);
        Reservation newReservation = new Reservation(id, "new test", reservedDateTime);
        // when
        boolean isEqual = reservation.equals(newReservation);
        // then
        assertThat(isEqual).isTrue();
    }
}
