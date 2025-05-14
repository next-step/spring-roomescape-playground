package roomescape;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;

public class DAOTest {

    private final ReservationDAO reservationDAO = new ReservationDAO();

    @BeforeEach
    void setUp() {
        reservationDAO.resetTable();
    }

    @Test
    public void connection() {
        try (final var connection = reservationDAO.getConnection()) {
            assertThat(connection).isNotNull();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addReservation() {
        final var reservation = new Reservation(1, "전서희", LocalDate.parse("2026-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);
    }

    @Test
    void findReservation() {
        final var reservation = new Reservation(1, "전서희", LocalDate.parse("2026-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);
        final var reservation2 = reservationDAO.findReservation(reservation.getId());
        assertThat(reservation2).isEqualTo(new Reservation(1, "전서희", LocalDate.parse("2026-05-12"), LocalTime.parse("19:00")));
    }

    @Test
    void deleteReservation() {
        final var reservation = new Reservation(1, "전서희", LocalDate.parse("2026-05-12"), LocalTime.parse("19:00"));
        reservationDAO.addReservation(reservation);
        reservationDAO.deleteReservation(reservation.getId());
        final var result = reservationDAO.findReservation(reservation.getId());
        assertThat(result).isNull();
    }

}
