package roomescape.service;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.dao.ReservationQueryingDao;
import roomescape.dao.ReservationUpdatingDao;
import roomescape.dao.TimeQueryingDao;
import roomescape.dao.TimeUpdatingDao;
import roomescape.domain.Reservation;
import roomescape.domain.Time;
import roomescape.dto.ReservationAddRequest;
import roomescape.dto.TimeAddRequest;
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

    public List<Reservation> findReservationList() {
        return reservationQueryingDao.selectListReservation();
    }

    public Reservation addReservation(final ReservationAddRequest request) {
        final Long timeId = request.getTime();
        final Time time = timeQueryingDao.selectTimeById(timeId);

        final Reservation reservation = new Reservation(request.getName(), request.getDate(), time);
        final Long reservationId = reservationUpdatingDao.createReservation(reservation);

        return Reservation.toEntity(reservationId, reservation);
    }

    public void removeReservation(final Long id) {
        int deleteCount = reservationUpdatingDao.deleteReservation(id);
        if (deleteCount == 0) {
            throw new NotFoundReservationException();
        }
    }

    public List<Time> findTimeList() {
        return timeQueryingDao.selectListTime();
    }

    public Time addTime(TimeAddRequest request) {
        final Time time = new Time(request.getTime());
        final Long id = timeUpdatingDao.createTime(time);

        return Time.toEntity(id, time);
    }

    public void removeTime(final Long id) {
        int deleteCount = timeUpdatingDao.deleteTime(id);
        if (deleteCount == 0) {
            throw new NotFoundTimeException();
        }
    }
}
