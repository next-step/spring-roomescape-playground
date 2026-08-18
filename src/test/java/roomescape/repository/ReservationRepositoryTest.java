package roomescape.repository;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationRepositoryTest {
    @Test
    void 예약을_추가하면_id가_정상적으로_부여되고_저장된다() {
        //Given
        ReservationRepository reservationRepository = new ReservationRepository();

        // When
        Reservation savedReservation = reservationRepository.addReservation(
                "이준환", LocalDate.of(2026, 8, 5), LocalTime.of(10, 0)
        );

        // Then
        assertThat(savedReservation.getId()).isEqualTo(1);
        assertThat(savedReservation.getName()).isEqualTo("이준환");
        assertThat(savedReservation.getDate()).isEqualTo(LocalDate.of(2026, 8, 5));
        assertThat(savedReservation.getTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 여러_예약을_추가하면_ID가_순차적으로_부여된다() {
        // Given
        ReservationRepository reservationRepository = new ReservationRepository();

        // When
        Reservation firstAddedReservation = reservationRepository.addReservation(
                "이준환", LocalDate.of(2026, 8, 5), LocalTime.of(10, 0)
        );
        Reservation secondAddedReservation = reservationRepository.addReservation(
                "김준우", LocalDate.of(2026, 8, 6), LocalTime.of(11, 0)
        );

        // Then
        assertThat(firstAddedReservation.getId()).isEqualTo(1);
        assertThat(secondAddedReservation.getId()).isEqualTo(2);
    }
}
