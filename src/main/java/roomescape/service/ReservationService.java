package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.util.List;

@Service
public class ReservationService {

    private final Reservations reservations;

    public ReservationService(Reservations reservations) {
        this.reservations = reservations;
    }

    public List<ReservationResponse> getAllReservations() {
        return reservations.getReservations().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation validatedReservation = Reservation.of(request.name(), request.date(), request.time());
        Reservation savedReservation = reservations.addReservation(validatedReservation);
        return ReservationResponse.from(savedReservation);
    }

    public void deleteReservation(Long id) {
        reservations.deleteReservationById(id);
    }
}
