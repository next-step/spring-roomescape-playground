package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationRequest;
import roomescape.exception.InvalidReservationRequestException;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ReservationList {

    private final ReservationDao reservationDao;
    private static final Logger logger = Logger.getLogger(ReservationList.class.getName());

    public ReservationList(ReservationDao reservationDao) {
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
        if (request.name() == null || request.name().isBlank()
                || request.date() == null || request.date().isBlank()
                || request.timeId() == null) {
            logger.warning("Invalid request: " + request);
            throw new InvalidReservationRequestException("잘못된 예약 요청입니다.");
        }

        long id = reservationDao.insert(request.name(), request.date(), request.timeId());
        Reservation saved = reservationDao.findById(id);
        return toResponse(saved);
    }

    public void delete(long id) {
        int updated = reservationDao.deleteById(id);
        if (updated == 0) {
            throw new NotFoundReservationException("해당 예약을 찾을 수 없습니다: " + id);
        }
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


