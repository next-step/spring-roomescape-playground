package roomescape.reservation.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.dto.ReservationResponse;

@Service
public class ReservationService {

    public List<ReservationResponse> getReservationResponses() {
        List<Reservation> reservations = Reservation.makeDummyData();
        List<ReservationResponse> reservationResponses = reservations.stream()
                .map(ReservationResponse::fromReservation)
                .collect(Collectors.toList());

        return reservationResponses;
    }
}
