package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dao.ReservationQueryingDao;
import roomescape.dao.ReservationUpdatingDao;
import roomescape.dao.TimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Service
public class ReservationService {
    private final ReservationQueryingDao reservationQueryingDao;
    private final ReservationUpdatingDao reservationUpdatingDao;
    private final TimeDao timeDao;

    public ReservationService(ReservationQueryingDao reservationDao, ReservationUpdatingDao reservationUpdatingDao,
        TimeDao timeDao) {
        this.reservationQueryingDao = reservationDao;
        this.reservationUpdatingDao = reservationUpdatingDao;
        this.timeDao = timeDao;
    }

    public List<Reservation> getAllReservations() {
        return reservationQueryingDao.listAllReservations();
    }

    public Long addReservation(Reservation reservation) {
        return reservationUpdatingDao.createReservation(reservation);
    }

    public int removeReservation(Long id) {
        return reservationUpdatingDao.deleteReservation(id);
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
