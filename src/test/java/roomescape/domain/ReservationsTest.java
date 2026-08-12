package roomescape.domain;

import org.junit.jupiter.api.Test;
import roomescape.exception.NotFoundReservationException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationsTest {

    @Test
    void 예약을_추가하면_id가_정상적으로_부여되고_저장된다() {
        //Given
        Reservations reservations = new Reservations();
        Reservation reservation = new Reservation(
                0,
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );
        // When
        reservations.add(reservation);

        // Then
        Reservation savedReservation = reservations.getReservations().get(0);

        assertThat(savedReservation.getId()).isEqualTo(1);
        assertThat(savedReservation.getName()).isEqualTo("이준환");
        assertThat(savedReservation.getDate()).isEqualTo(LocalDate.of(2026, 8, 5));
        assertThat(savedReservation.getTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 여러_예약을_추가하면_ID가_순차적으로_부여된다() {
        // Given
        Reservations reservations = new Reservations();

        Reservation firstReservation = new Reservation(
                0,
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );

        Reservation secondReservation = new Reservation(
                0,
                "김준우",
                LocalDate.of(2026, 8, 6),
                LocalTime.of(11, 0)
        );

        // When
        Reservation firstAdded = reservations.add(firstReservation);
        Reservation secondAdded = reservations.add(secondReservation);

        // Then
        assertThat(firstAdded.getId()).isEqualTo(1);
        assertThat(secondAdded.getId()).isEqualTo(2);
    }

    @Test
    void 예약을_삭제하면_목록에서_제거된다() {
        // Given
        Reservations reservations = new Reservations();
        Reservation reservation = new Reservation(
                0,
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );
        Reservation addedReservation = reservations.add(reservation);

        // When
        reservations.delete(addedReservation.getId());

        // Then
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
}
