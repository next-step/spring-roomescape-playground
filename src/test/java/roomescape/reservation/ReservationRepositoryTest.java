package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        reservationRepository.save(new Reservation(null, "브라운", LocalDate.of(2026, 8, 5), LocalTime.of(15, 40)));
        reservationRepository.save(new Reservation(null, "포비", LocalDate.of(2026, 8, 6), LocalTime.of(16, 40)));

        List<Reservation> reservations = reservationRepository.findAll();

        Reservation reservation = reservations.get(0);

        assertThat(reservation.name()).isEqualTo("브라운");
        assertThat(reservation.date()).isEqualTo(LocalDate.of(2026, 8, 5));
        assertThat(reservation.time()).isEqualTo(LocalTime.of(15, 40));

        reservation = reservations.get(1);

        assertThat(reservation.name()).isEqualTo("포비");
        assertThat(reservation.date()).isEqualTo(LocalDate.of(2026, 8, 6));
        assertThat(reservation.time()).isEqualTo(LocalTime.of(16, 40));
    }

    @Test
    void deleteReservationWithNonExistingIdThrowsException() {
        assertThatThrownBy(() -> reservationRepository.deleteById(new ReservationId(999L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void saveNullNameThrowsException() {
        assertThatThrownBy(() -> reservationRepository.save(new Reservation(
                null, null,
                LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40)
        ))).isInstanceOf(DataAccessException.class);
    }

    @Test
    void saveTooLongNameThrowsException() {
        assertThatThrownBy(() -> reservationRepository.save(new Reservation(
                null, "a".repeat(256),
                LocalDate.of(2026, 8, 5),
                LocalTime.of(15, 40)
        ))).isInstanceOf(DataAccessException.class);
    }


}