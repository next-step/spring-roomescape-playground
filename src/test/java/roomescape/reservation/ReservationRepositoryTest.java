package roomescape.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.Date;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.exception.NotFoundException;
import roomescape.reservation.domain.Reservation;
import roomescape.time.domain.Time;

@JdbcTest
@Import(ReservationRepository.class)
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void saveReservation() {
        Long timeId = saveTime("10:00");

        Reservation response = reservationRepository.save(new Reservation(
                null,
                "브라운",
                Date.valueOf("2026-08-05"),
                new Time(timeId, "10:00")
        ));

        assertThat(response.getId()).isNotNull();
        assertThat(response.getName()).isEqualTo("브라운");
        assertThat(response.getDate()).isEqualTo(Date.valueOf("2026-08-05"));
        assertThat(response.getTime().getId()).isEqualTo(timeId);
    }

    @Test
    void findAllReturnsAllReservations() {
        Long timeId1 = saveTime("10:00");
        Long timeId2 = saveTime("11:00");

        reservationRepository.save(new Reservation(
                null,
                "브라운",
                Date.valueOf("2026-08-05"),
                new Time(timeId1, "10:00")
        ));

        reservationRepository.save(new Reservation(
                null,
                "포비",
                Date.valueOf("2026-08-06"),
                new Time(timeId2, "11:00")
        ));

        Collection<Reservation> reservations = reservationRepository.findAll();

        assertThat(reservations).hasSize(2);

        assertThat(reservations)
                .extracting(Reservation::getName)
                .containsExactly("브라운", "포비");

        assertThat(reservations)
                .extracting(reservation -> reservation.getTime().getFormattedTime())
                .containsExactly("10:00", "11:00");
    }

    @Test
    void deleteReservation() {
        Long timeId = saveTime("10:00");

        Reservation response = reservationRepository.save(new Reservation(
                null,
                "브라운",
                Date.valueOf("2026-08-05"),
                new Time(timeId, "10:00")
        ));

        reservationRepository.deleteById(response.getId());

        assertThat(reservationRepository.findAll()).isEmpty();
    }

    @Test
    void deleteReservationWithNonExistingIdThrowsException() {
        assertThatThrownBy(() -> reservationRepository.deleteById(999L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void saveNullNameThrowsException() {
        Long timeId = saveTime("10:00");

        assertThatThrownBy(() -> reservationRepository.save(new Reservation(
                null,
                null,
                Date.valueOf("2026-08-05"),
                new Time(timeId, "10:00")
        ))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void saveTooLongNameThrowsException() {
        Long timeId = saveTime("10:00");

        assertThatThrownBy(() -> reservationRepository.save(new Reservation(
                null,
                "a".repeat(11),
                Date.valueOf("2026-08-05"),
                new Time(timeId, "10:00")
        ))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void saveInvalidTimeIdThrowsException() {
        assertThatThrownBy(() -> reservationRepository.save(new Reservation(
                null,
                "브라운",
                Date.valueOf("2026-08-05"),
                new Time(999L, "10:00")
        ))).isInstanceOf(DataAccessException.class);
    }

    private Long saveTime(String time) {
        jdbcTemplate.update("INSERT INTO time(time) VALUES (?)", time);

        return jdbcTemplate.queryForObject(
                "SELECT id FROM time WHERE time = ?",
                Long.class,
                time
        );
    }
}
