package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationRequest;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ReservationService    {

    private final ReservationDao reservationDao;
    private static final Logger logger = Logger.getLogger(ReservationService.class.getName());

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> findAll() {
        List<Reservation> results = reservationDao.findAll();
        List<ReservationResponse> responses = new ArrayList<>();
        for (Reservation reservation : results) {
            responses.add(toResponse(reservation));
        }
        return responses;
    }

    public ReservationResponse create(ReservationRequest request) {
        Reservation reservation = new Reservation(
                null,
                request.name(),
                request.date(),
                new Time(request.timeId(), null)
        );
        long id = reservationDao.insert(reservation);
        Reservation saved = reservationDao.findById(id);
        return toResponse(saved);
    }

    public void delete(long id) {
        reservationDao.deleteById(id);
    }

    private ReservationResponse toResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime().getTime()
        );
    }
}


