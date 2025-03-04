package roomescape.service;


import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.reservation.ReservationJdbcDAO;
import roomescape.entity.Reservation;

@Service
public class ReservationService {

    private final ReservationJdbcDAO reservationJdbcDAO;

    public ReservationService(ReservationJdbcDAO reservationJdbcDAO) {
        this.reservationJdbcDAO = reservationJdbcDAO;
    }

    public List<Reservation> getAllReservations() {
        return reservationJdbcDAO.getAll();
    }

    public Reservation createReservation(Reservation reservation) {
        if (reservation.getDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Reservation date must be after today.");
        }
        return reservationJdbcDAO.create(reservation);
    }

    public Reservation getReservationById(long id) {
        Reservation reservation = reservationJdbcDAO.getById(id);
        if (reservation == null) {
            throw new IllegalArgumentException("No reservation found with the given ID.");
        }
        return reservationJdbcDAO.getById(id);
    }

    public void deleteReservationById(long id) {
        Reservation reservation = reservationJdbcDAO.getById(id);
        if (reservation == null) {
            throw new IllegalArgumentException("No reservation found with the given ID.");
        }
        reservationJdbcDAO.delete(id);
    }

}
