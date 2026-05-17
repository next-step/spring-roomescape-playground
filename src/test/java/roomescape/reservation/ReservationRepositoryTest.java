package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.ReservationId;

@JdbcTest
@Import(ReservationRepository.class)
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    void findAllReturnsAllReservations() {
        reservationRepository.save(new Reservation(null, "브라운", LocalDate.of(2023, 8, 5), LocalTime.of(15, 40)));
        reservationRepository.save(new Reservation(null, "포비", LocalDate.of(2023, 8, 6), LocalTime.of(16, 40)));

        List<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(2);
    }

    @Test
    void deleteReservationWithNonExistingIdThrowsException() {
        assertThatThrownBy(() -> reservationRepository.deleteById(new ReservationId(999L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void saveTooLongNameThrowsException() {
        String name = "a".repeat(256);

        assertThatThrownBy(() -> reservationRepository.save(new Reservation(
                null, name,
                LocalDate.of(2026, 8, 5),
                LocalTime.of(15, 40)
        ))).isInstanceOf(DataAccessException.class);
    }

    @Test
    void saveNullNameThrowsException() {
        assertThatThrownBy(() -> reservationRepository.save(new Reservation(
                null, null,
                LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40)
        ))).isInstanceOf(DataAccessException.class);
    }
}