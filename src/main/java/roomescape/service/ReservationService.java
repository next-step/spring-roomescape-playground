package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dao.ReservationQueryingDao;
import roomescape.dao.ReservationUpdatingDao;
import roomescape.dao.TimeQueryingDao;
import roomescape.dao.TimeUpdatingDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.domain.dto.ReservationAddRequest;
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

    private static boolean isValidReservation(ReservationAddRequest reservationAddRequest) {
        if (reservationAddRequest == null)
            return false;
        if (reservationAddRequest.getName().isEmpty() || reservationAddRequest.getName().isBlank())
            return false;
        if (reservationAddRequest.getDate() == null)
            return false;

        return reservationAddRequest.getTime() != null;
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

    public Reservation addReservation(ReservationAddRequest reservationAddRequest) {
        if (!isValidReservation(reservationAddRequest)) {
            throw new InvalidReservationException();
        }

        Time registeredTime = timeQueryingDao.getTime(reservationAddRequest.getTime()).get(0);
        Long generatedId = reservationUpdatingDao.createReservation(reservationAddRequest);

        return new Reservation(
            generatedId,
            reservationAddRequest.getName(),
            reservationAddRequest.getDate(),
            registeredTime.getId(),
            registeredTime.getTime()
        );
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
