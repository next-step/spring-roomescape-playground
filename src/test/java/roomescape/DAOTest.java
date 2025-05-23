package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.dao.ReservationDAO;
import roomescape.dao.TimeDAO;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@JdbcTest
@Import({ReservationDAO.class, TimeDAO.class})
public class DAOTest {

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private TimeDAO timeDAO;

    @BeforeEach
    void setUp() {
        reservationDAO.findAll().forEach(reservation ->
                reservationDAO.delete(reservation.getId()));
    }

    @Test
    void addReservation() {
        Time time = timeDAO.save(new Time(null, "19:00"));
        Reservation reservation = new Reservation(null, "전서희", LocalDate.of(2026, 5, 12), time);

        Reservation saved = reservationDAO.add(reservation);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("전서희");
        assertThat(saved.getDate()).isEqualTo(LocalDate.of(2026, 5, 12));
        assertThat(saved.getTime().getTime()).isEqualTo("19:00");
    }

    @Test
    void findReservation() {
        Time time = timeDAO.save(new Time(null, "19:00"));
        Reservation saved = reservationDAO.add(
                new Reservation(null, "전서희", LocalDate.of(2026, 5, 12), time));

        Optional<Reservation> found = reservationDAO.findByID(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(saved);
    }

    @Test
    void deleteReservation() {
        Time time = timeDAO.save(new Time(null, "19:00"));
        Reservation saved = reservationDAO.add(
                new Reservation(null, "전서희", LocalDate.of(2026, 5, 12), time));

        reservationDAO.delete(saved.getId());

        Optional<Reservation> result = reservationDAO.findByID(saved.getId());
        assertThat(result).isEmpty();
    }

    @Test
    void updateReservation() {
        Time time = timeDAO.save(new Time(null, "14:00"));
        Reservation saved = reservationDAO.add(
                new Reservation(null, "전서희", LocalDate.of(2026, 5, 12), time));

        Time newTime = timeDAO.save(new Time(null, "17:00"));
        Reservation updated = new Reservation(saved.getId(), "서희전", LocalDate.of(2026, 5, 13), newTime);
        reservationDAO.update(updated);

        Optional<Reservation> result = reservationDAO.findByID(saved.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("서희전");
        assertThat(result.get().getDate()).isEqualTo(LocalDate.of(2026, 5, 13));
        assertThat(result.get().getTime().getTime()).isEqualTo("17:00");
    }
}
