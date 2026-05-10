package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.Reservations;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReservationService {
    private final Reservations reservations;
    private final AtomicLong index;

    public ReservationService() {
        reservations = new Reservations();
        index = new AtomicLong(reservations.getReservationCount() + 1);
    }

    public List<ReservationResponse> getAllReservations() {
        return reservations.getReservations().stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public ReservationResponse createReservation(ReservationRequest request) {
        Reservation reservation = new Reservation(request.name(), request.date(), request.time());
        reservation.setId(index.getAndIncrement());

        reservations.addReservation(reservation);
        return ReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        reservations.deleteReservationById(id);
    }
}
