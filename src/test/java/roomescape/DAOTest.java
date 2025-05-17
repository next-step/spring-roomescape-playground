package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DAOTest {

    @Autowired
    private ReservationDAO reservationDAO;

    @BeforeEach
    void setUp() {
        reservationDAO.findAll().forEach(reservation ->
                reservationDAO.deleteReservation(reservation.getId()));
    }

    @Test
    void addReservation() {
        Reservation reservation = new Reservation(null, "전서희", LocalDate.of(2026, 5, 12), LocalTime.of(19, 0));

        Reservation saved = reservationDAO.addReservation(reservation);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("전서희");
        assertThat(saved.getDate()).isEqualTo(LocalDate.of(2026, 5, 12));
        assertThat(saved.getTime()).isEqualTo(LocalTime.of(19, 0));
    }

    @Test
    void findReservation() {
        Reservation saved = reservationDAO.addReservation(
                new Reservation(null, "전서희", LocalDate.of(2026, 5, 12), LocalTime.of(19, 0)));

        Optional<Reservation> found = reservationDAO.findByID(saved.getId());

        assertThat(found).isPresent();
        assertThat(found.get()).isEqualTo(saved);
    }

    @Test
    void deleteReservation() {
        Reservation reservation = new Reservation(1, "전서희", LocalDate.parse("2026-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);
        reservationDAO.deleteReservation(1);

        Optional<Reservation> result = reservationDAO.findByID(1);
        assertThat(result).isEmpty();
    }

    @Test
    void updateReservation() {
        LocalDate date = LocalDate.now().plusDays(1);
        Reservation original = new Reservation(null, "전서희", date, LocalTime.of(19, 0));
        Reservation saved = reservationDAO.addReservation(original);

        Reservation updated = new Reservation(saved.getId(), "서희전", date.plusDays(1), LocalTime.of(14, 0));
        reservationDAO.updateReservation(updated);

        Optional<Reservation> result = reservationDAO.findByID(saved.getId());
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("서희전");
        assertThat(result.get().getDate()).isEqualTo(date.plusDays(1));
        assertThat(result.get().getTime()).isEqualTo(LocalTime.of(14, 0));
    }
}
