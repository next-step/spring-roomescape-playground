package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservationDao.findAll().stream()
            .map(ReservationResponse::from)
            .toList();
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation reservation = new Reservation(null, request.name(), request.date(),
            request.time());
        Reservation saved = reservationDao.save(reservation);
        return ReservationResponse.from(saved);
    }

    public void deleteReservation(long id) {
        reservationDao.deleteById(id);
    }
}
