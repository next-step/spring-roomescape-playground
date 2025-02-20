package roomescape.service;

import java.util.List;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationDAO;

@Service
public class ReservationService {
    private final ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public List<Reservation> showReservations() {
        return reservationDAO.findReservations();
    }

    public ReservationResponse reserve(ReservationCreateRequest request) {
        return reservationDAO.createReservation(request);
    }

    public void cancelReservation(Long reservationId) {
        reservationDAO.deleteReservation(reservationId);
    }
}
