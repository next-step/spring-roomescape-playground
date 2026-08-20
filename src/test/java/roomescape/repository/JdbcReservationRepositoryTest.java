package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestExecutionListeners;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
class JdbcReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 저장된_예약_목록을_정상적으로_반환한다() {
        // Given
        JdbcReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);

        reservationRepository.save(
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );

        // When
        List<Reservation> reservations = reservationRepository.findAll();

        // Then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getName()).isEqualTo("이준환");
        assertThat(reservations.get(0).getDate()).isEqualTo(LocalDate.of(2026, 8, 5));
        assertThat(reservations.get(0).getTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 예약을_추가하면_id가_정상적으로_부여되고_저장된다() {
        // Given
        JdbcReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);

        // When
        Reservation savedReservation = reservationRepository.save(
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );

        // Then
        assertThat(savedReservation.getId()).isPositive();
    }

    @Test
    void 여러_예약을_추가하면_ID가_순차적으로_부여된다() {
        // Given
        JdbcReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);

        // When
        Reservation firstAddedReservation = reservationRepository.save(
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );
        Reservation secondAddedReservation = reservationRepository.save(
                "김준우",
                LocalDate.of(2026, 8, 6),
                LocalTime.of(11, 0)
        );

        // Then
        assertThat(firstAddedReservation.getId())
                .isLessThan(secondAddedReservation.getId());
    }

    @Test
    void 삭제하려는_id의_예약이_존재하면_삭제에_성공한다() {
        // Given
        JdbcReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);

        Reservation reservation = reservationRepository.save(
                "이준환",
                LocalDate.of(2026, 8, 5),
                LocalTime.of(10, 0)
        );

        // When
        boolean deleted = reservationRepository.deleteById(reservation.getId());

        // Then
        assertThat(deleted).isTrue();
        assertThat(reservationRepository.findAll()).isEmpty();
    }

    @Test
    void 삭제하려는_id의_예약이_존재하지_않으면_삭제에_실패한다() {
        // Given
        JdbcReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);

        // When
        boolean deleted = reservationRepository.deleteById(999);

        // Then
        assertThat(deleted).isFalse();
    }
}
