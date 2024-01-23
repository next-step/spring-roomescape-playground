package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<Reservation> getAllReservations() {
        return reservationDao.listAllReservations();
    }

    public Long addReservation(Reservation reservation) {
        return reservationDao.createReservation(reservation);
    }

    public int removeReservation(Long id) {
        return reservationDao.deleteReservation(id);
    }
}
