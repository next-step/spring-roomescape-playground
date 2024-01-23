package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dao.ReservationDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationDao reservationDao, TimeDao timeDao) {
        this.reservationDao = reservationDao;
        this.timeDao = timeDao;
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

    public List<Time> getAllTimes() {
        return timeDao.listAllTimes();
    }

    public Long addTime(Time time) {
        return timeDao.createTime(time);
    }

    public int removeTime(Long id) {
        return timeDao.deleteTime(id);
    }
}
