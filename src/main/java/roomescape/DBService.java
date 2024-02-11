package roomescape;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.ReservationDAO;

import java.util.List;

@Service
public class DBService {

    private final ReservationDAO reservationDAO;

    @Autowired
    public DBService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> getAllReservations() {
        return reservationDAO.findAllReservations();
    }

    public Reservation addReservation(Reservation reservation) {
        return reservationDAO.insertReservation(reservation);
    }

    public void cancelReservation(Long id) {
        reservationDAO.deleteReservation(id);
    }
}
