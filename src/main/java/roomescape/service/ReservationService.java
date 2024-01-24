package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dao.ReservationQueryingDao;
import roomescape.dao.ReservationUpdatingDao;
import roomescape.dao.TimeQueryingDao;
import roomescape.dao.TimeUpdatingDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

@Service
public class ReservationService {
    private final ReservationQueryingDao reservationQueryingDao;
    private final ReservationUpdatingDao reservationUpdatingDao;
    private final TimeQueryingDao timeQueryingDao;
    private final TimeUpdatingDao timeUpdatingDao;

    public ReservationService(ReservationQueryingDao reservationQueryingDao, ReservationUpdatingDao reservationUpdatingDao,
        TimeQueryingDao timeQueryingDao, TimeUpdatingDao timeUpdatingDao) {
        this.reservationQueryingDao = reservationQueryingDao;
        this.reservationUpdatingDao = reservationUpdatingDao;
        this.timeQueryingDao = timeQueryingDao;
        this.timeUpdatingDao = timeUpdatingDao;
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
        return timeQueryingDao.listAllTimes();
    }

    public Long addTime(Time time) {
        return timeUpdatingDao.createTime(time);
    }

    public int removeTime(Long id) {
        return timeUpdatingDao.deleteTime(id);
    }
}
