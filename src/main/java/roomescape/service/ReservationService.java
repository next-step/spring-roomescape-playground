package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDAO;
import roomescape.domain.Reservation;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;

@Service
public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public Reservation add(Reservation reservation) {
        boolean isDuplicate = reservationDAO.findAll().stream().anyMatch(r ->
                r.getName().equals(reservation.getName()) &&
                        r.getDate().equals(reservation.getDate()) &&
                        r.getTime().equals(reservation.getTime())
        );
        if (isDuplicate) {
            throw new InvalidReservationException("동일한 예약이 이미 존재합니다.");
        }

        return reservationDAO.addReservation(reservation);
    }

    public List<Reservation> findAll() {
        return reservationDAO.findAll();
    }

    public void delete(int id) {
        boolean exists = reservationDAO.findByID(id).isPresent();
        if (!exists) {
            throw new NotFoundReservationException("해당 ID가 없습니다.");
        }
        reservationDAO.deleteReservation(id);
    }

    public void update(Reservation reservation) {
        reservationDAO.updateReservation(reservation);
    }
}
