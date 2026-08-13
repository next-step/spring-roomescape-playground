package roomescape.domain;

import org.junit.jupiter.api.Test;
import roomescape.exception.NotFoundReservationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationsTest {
    @Test
    void 예약을_삭제하면_목록에서_제거된다() {
        // Given
        Reservations reservations = new Reservations();
        Reservation reservation = new Reservation(
                1,
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );
        reservations.add(reservation);

        //
        reservations.delete(reservation.getId());

        // When & Then
        assertThat(reservations.getReservations()).isEmpty();
    }

    @Test
    void 존재하지_않는_예약을_삭제하면_예외가_발생한다() {
        // Given
        Reservations reservations = new Reservations();

        // When & Then
        assertThatThrownBy(() -> reservations.delete(999))
                .isInstanceOf(NotFoundReservationException.class)
                .hasMessage("해당 id의 예약을 찾을 수 없습니다.");
    }

    @Test
    void 여러_예약_중_특정_id의_예약만_삭제된다() {
        //Given
        Reservations reservations = new Reservations();
        Reservation first = new Reservation(1, "이준환", LocalDate.of(2026,8,5), LocalTime.of(10,0));
        Reservation second = new Reservation(2, "이준환", LocalDate.of(2026,8,6), LocalTime.of(11,0));
        reservations.add(first);
        reservations.add(second);

        //When
        reservations.delete(1);

        //Then
        List<Reservation> remaining = reservations.getReservations();
        assertThat(remaining).hasSize(1);
        assertThat(remaining.get(0).getId()).isEqualTo(2);
    }

    @Test
    void getReservations로_받은_리스트는_외부에서_수정할_수_없다() {
        // Given
        Reservations reservations = new Reservations();
        Reservation first = new Reservation(1, "이준환", LocalDate.of(2026, 8, 5), LocalTime.of(10, 0));
        Reservation second = new Reservation(2, "이준환2", LocalDate.of(2026, 8, 6), LocalTime.of(11, 0));
        reservations.add(first);
        reservations.add(second);

        // When & Then
        List<Reservation> list = reservations.getReservations();
        assertThatThrownBy(() -> list.add(new Reservation(2, "이준환3", LocalDate.of(2026, 8, 6), LocalTime.of(11, 0))))
                .isInstanceOf(UnsupportedOperationException.class);
    }
}
