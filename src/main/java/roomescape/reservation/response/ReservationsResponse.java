package roomescape.reservation.response;

import java.util.List;
import roomescape.reservation.model.Reservation;

public record ReservationsResponse(List<ReservationResponse> reservations) {

    public static ReservationsResponse of(List<Reservation> reservations) {
        return new ReservationsResponse(reservations.stream()
            .map(ReservationResponse::new)
            .toList());
    }
}
