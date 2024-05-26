package roomescape.reservation.presentation.dto;

import roomescape.reservation.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {

    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time().time()
        );
    }
}
