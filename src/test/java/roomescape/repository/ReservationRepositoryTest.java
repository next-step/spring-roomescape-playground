package roomescape.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:repository-test")
class ReservationRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 예약을_추가하면_id가_정상적으로_부여되고_저장된다() {
        //Given
        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);

        // When
        Reservation savedReservation = reservationRepository.addReservation(
                "이준환", LocalDate.of(2026, 8, 5), LocalTime.of(10, 0)
        );

        // Then
        assertThat(savedReservation.getId()).isPositive();
    }

    @Test
    void 여러_예약을_추가하면_ID가_순차적으로_부여된다() {
        // Given
        ReservationRepository reservationRepository = new ReservationRepository(jdbcTemplate);

        // When
        Reservation firstAddedReservation = reservationRepository.addReservation(
                "이준환", LocalDate.of(2026, 8, 5), LocalTime.of(10, 0)
        );
        Reservation secondAddedReservation = reservationRepository.addReservation(
                "김준우", LocalDate.of(2026, 8, 6), LocalTime.of(11, 0)
        );

        // Then
        assertThat(firstAddedReservation.getId()).isLessThan(secondAddedReservation.getId());
    }
}
