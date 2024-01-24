package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dao.ReservationQueryingDao;
import roomescape.dao.ReservationUpdatingDao;
import roomescape.dao.TimeQueryingDao;
import roomescape.dao.TimeUpdatingDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.InvalidTimeException;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;

@Service
public class ReservationService {
    private final ReservationQueryingDao reservationQueryingDao;
    private final ReservationUpdatingDao reservationUpdatingDao;
    private final TimeQueryingDao timeQueryingDao;
    private final TimeUpdatingDao timeUpdatingDao;

    public ReservationService(ReservationQueryingDao reservationQueryingDao,
        ReservationUpdatingDao reservationUpdatingDao,
        TimeQueryingDao timeQueryingDao, TimeUpdatingDao timeUpdatingDao) {
        this.reservationQueryingDao = reservationQueryingDao;
        this.reservationUpdatingDao = reservationUpdatingDao;
        this.timeQueryingDao = timeQueryingDao;
        this.timeUpdatingDao = timeUpdatingDao;
    }

    private static boolean isValidReservation(Reservation reservation) {
        if (reservation == null)
            return false;
        if (reservation.getName().isEmpty() || reservation.getName().isBlank())
            return false;
        if (reservation.getDate() == null)
            return false;
        return reservation.getTime() != null;
    }

    private static boolean isValidTime(Time time) {
        if (time == null)
            return false;
        if (time.getTime() == null)
            return false;
        return true;
    }

    public List<Reservation> getAllReservations() {
        return reservationQueryingDao.listAllReservations();
    }

    public Reservation addReservation(Reservation reservation) {
        if (!isValidReservation(reservation)) {
            throw new InvalidReservationException();
        }

        Long generatedId = reservationUpdatingDao.createReservation(reservation);
        Reservation newReservation = Reservation.toEntity(reservation, generatedId);
        return newReservation;
    }

    public void removeReservation(Long id) {
        int deleteCount = reservationUpdatingDao.deleteReservation(id);
        if (deleteCount == 0) {
            throw new NotFoundReservationException();
        }
    }

    public List<Time> getAllTimes() {
        return timeQueryingDao.listAllTimes();
    }

    public Time addTime(Time time) {
        if (!isValidTime(time)) {
            throw new InvalidTimeException();
        }

        Long generatedId = timeUpdatingDao.createTime(time);
        Time newTime = Time.toEntity(time, generatedId);
        return newTime;
    }

    public void removeTime(Long id) {
        int deleteCount = timeUpdatingDao.deleteTime(id);
        if (deleteCount == 0) {
            throw new NotFoundTimeException();
        }
    }
}
