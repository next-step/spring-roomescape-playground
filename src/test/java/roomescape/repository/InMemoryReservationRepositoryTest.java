package roomescape.repository;

import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class InMemoryReservationRepositoryTest {

    @Test
    void 예약을_삭제하면_목록에서_제거된다() {
        // Given
        InMemoryReservationRepository reservations = new InMemoryReservationRepository();
        Reservation reservation = reservations.save(
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );

        // When
        boolean deleted = reservations.deleteById(reservation.getId());

        // Then
        assertThat(deleted).isTrue();
        assertThat(reservations.findAll()).isEmpty();
    }

    @Test
    void 존재하지_않는_예약을_삭제하면_false를_반환한다() {
        // Given
        InMemoryReservationRepository reservations = new InMemoryReservationRepository();

        // When
        boolean deleted = reservations.deleteById(999);

        // Then
        assertThat(deleted).isFalse();
    }

    @Test
    void 여러_예약_중_특정_id의_예약만_삭제된다() {
        // Given
        InMemoryReservationRepository reservations = new InMemoryReservationRepository();

        Reservation first = reservations.save(
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );
        Reservation second = reservations.save(
                "이준환",
                LocalDate.of(2026, 8, 6),
                LocalTime.of(11, 0)
        );

        // When
        boolean deleted = reservations.deleteById(first.getId());

        // Then
        assertThat(deleted).isTrue();

        List<Reservation> remaining = reservations.findAll();
        assertThat(remaining).hasSize(1);
        assertThat(remaining.get(0).getId()).isEqualTo(second.getId());
    }

    @Test
    void findAll로_받은_리스트는_외부에서_수정할_수_없다() {
        // Given
        InMemoryReservationRepository reservations = new InMemoryReservationRepository();
        reservations.save(
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );
        reservations.save(
                "이준환2",
                LocalDate.of(2026, 8, 6),
                LocalTime.of(11, 0)
        );

        // When
        List<Reservation> list = reservations.findAll();

        // Then
        assertThat(list).hasSize(2);
        assertThatThrownBy(() -> list.add(
                new Reservation(
                        999,
                        "이준환3",
                        LocalDate.of(2026, 8, 6),
                        LocalTime.of(11, 0)
                )
        )).isInstanceOf(UnsupportedOperationException.class);
    }
}
