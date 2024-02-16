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
import roomescape.dto.ReservationResponse;
import roomescape.dto.TimeAddRequest;
import roomescape.dto.TimeResponse;
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

    public List<ReservationResponse> findReservationList() {
        return reservationQueryingDao.selectListReservation()
            .stream()
            .map(ReservationResponse::new)
            .toList();
    }

    public ReservationResponse addReservation(final ReservationAddRequest request) {
        final Long timeId = request.getTime();
        final Time time = timeQueryingDao.selectTimeById(timeId);

        final Reservation reservation = new Reservation(request.getName(), request.getDate(), time);
        final Long reservationId = reservationUpdatingDao.createReservation(reservation);
        final Reservation reservationEntity = Reservation.toEntity(reservationId, reservation);

        return new ReservationResponse(reservationEntity);
    }

    public void removeReservation(final Long id) {
        int deleteCount = reservationUpdatingDao.deleteReservation(id);
        if (deleteCount == 0) {
            throw new NotFoundReservationException();
        }
    }

    public List<TimeResponse> findTimeList() {
        return timeQueryingDao.selectListTime()
            .stream()
            .map(TimeResponse::new)
            .toList();
    }

    public TimeResponse addTime(TimeAddRequest request) {
        final Time time = new Time(request.getTime());
        final Long id = timeUpdatingDao.createTime(time);
        final Time timeEntity = Time.toEntity(id, time);

        return new TimeResponse(timeEntity);
    }

    public void removeTime(final Long id) {
        int deleteCount = timeUpdatingDao.deleteTime(id);
        if (deleteCount == 0) {
            throw new NotFoundTimeException();
        }
    }
}
