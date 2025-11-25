package roomescape.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationRequest;
import roomescape.exception.InvalidReservationRequestException;
import roomescape.exception.NotFoundReservationException;
import roomescape.domain.Reservation;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationList {

    private final ReservationDao reservationDao;

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
                || request.time() == null || request.time().isBlank()) {
            throw new InvalidReservationRequestException(
                    "잘못된 예약 요청입니다. " +
                            "name=" + request.name() +
                            ", date=" + request.date() +
                            ", time=" + request.time()
            );
        }

        long id = reservationDao.insert(request.name(), request.date(), request.time());
        return new ReservationResponse(id, request.name(), request.date(), request.time());
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
                reservation.getTime()
        );
    }
}


