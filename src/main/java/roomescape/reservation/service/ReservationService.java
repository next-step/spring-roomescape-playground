package roomescape.reservation.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;

@Service
public class ReservationService {

    public List<ReservationResponse> getReservationResponses(List<Reservation> reservations) {
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::fromReservation)
                .collect(Collectors.toList());

        return reservationResponses;
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest,
                                                 List<Reservation> reservations, Long id) {
        Reservation newReservation = ReservationRequest
                .toReservation(reservationRequest, id);
        reservations.add(newReservation);

        return ReservationResponse.fromReservation(newReservation);
    }

    public void deleteReservation(List<Reservation> reservations, Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
    }
}
