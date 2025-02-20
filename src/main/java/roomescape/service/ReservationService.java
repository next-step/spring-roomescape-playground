package roomescape.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationCreateRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationDAO;

@Service
public class ReservationService {
    private final AtomicLong index = new AtomicLong(0);
    private final List<Reservation> reservations = new ArrayList<>();
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
