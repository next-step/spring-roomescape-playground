package roomescape.service;


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
        return reservationJdbcDAO.create(reservation);
    }

    public Reservation getReservationById(long id) {
        return reservationJdbcDAO.getById(id);
    }

    public void deleteReservationById(long id) {
        reservationJdbcDAO.delete(id);
    }

}
