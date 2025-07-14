package roomescape.reservation.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.exception.ReservationNotFoundException;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.request.ReservationRequest;
import roomescape.reservation.response.ReservationResponse;
import roomescape.time.model.Time;
import roomescape.time.service.TimeService;

@Service
public class ReservationService {

    private final TimeService timeService;
    private final ReservationDao reservationDao;

    public ReservationService(TimeService timeService, ReservationDao reservationDao) {
        this.timeService = timeService;
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> getAllReservation() {
        return reservationDao.findAll().stream()
            .map(ReservationResponse::of)
            .toList();
    }

    public ReservationResponse create(ReservationRequest request) {
        Time time = timeService.getTime(request.time());
        Reservation savedReservation = reservationDao.insert(
            time,
            request.name(),
            request.date()
        );

        return ReservationResponse.of(savedReservation);
    }

    public void delete(Long id) {
        if (!reservationDao.delete(id)) {
            throw new ReservationNotFoundException("Reservation not found with id: " + id);
        }
    }
}
