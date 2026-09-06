package roomescape.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class JdbcReservationRepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private TimeRepository timeRepository;

    @BeforeEach
    void setUp() {
        timeRepository = new TimeRepository(jdbcTemplate);
    }

    @Test
    void 저장된_예약_목록을_정상적으로_반환한다() {
        // Given
        JdbcReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);

        Time savedTime = timeRepository.save(new Time(LocalTime.of(10, 0)));
        reservationRepository.save(
                new Reservation("이준환", LocalDate.of(2026, 8, 5), savedTime)
        );

        // When
        List<Reservation> reservations = reservationRepository.findAll();

        // Then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.get(0).getName()).isEqualTo("이준환");
        assertThat(reservations.get(0).getDate()).isEqualTo(LocalDate.of(2026, 8, 5));
        assertThat(reservations.get(0).getTime().getTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 예약을_추가하면_id가_정상적으로_부여되고_저장된다() {
        // Given
        JdbcReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);
        Time savedTime = timeRepository.save(new Time(LocalTime.of(10, 0)));

        // When
        Reservation savedReservation = reservationRepository.save(
                new Reservation("이준환", LocalDate.of(2026, 8, 5), savedTime)
        );

        // Then
        assertThat(savedReservation.getId()).isPositive();
    }

    @Test
    void 여러_예약을_추가하면_ID가_순차적으로_부여된다() {
        // Given
        JdbcReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);
        Time firstTime = timeRepository.save(new Time(LocalTime.of(10, 0)));
        Time secondTime = timeRepository.save(new Time(LocalTime.of(11, 0)));

        // When
        Reservation firstAddedReservation = reservationRepository.save(
                new Reservation("이준환", LocalDate.of(2026, 8, 5), firstTime)
        );
        Reservation secondAddedReservation = reservationRepository.save(
                new Reservation("김준우", LocalDate.of(2026, 8, 6), secondTime)
        );

        // Then
        assertThat(firstAddedReservation.getId())
                .isLessThan(secondAddedReservation.getId());
    }

    @Test
    void 삭제하려는_id의_예약이_존재하면_삭제에_성공한다() {
        // Given
        JdbcReservationRepository reservationRepository = new JdbcReservationRepository(jdbcTemplate);
        Time savedTime = timeRepository.save(new Time(LocalTime.of(10, 0)));

        Reservation reservation = reservationRepository.save(
                new Reservation("이준환", LocalDate.of(2026, 8, 5), savedTime)
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
