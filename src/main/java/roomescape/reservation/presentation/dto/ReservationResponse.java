package roomescape.reservation.presentation.dto;

import java.time.LocalDateTime;
import roomescape.reservation.domain.Reservation;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {

    public static ReservationResponse from(Reservation reservation) {
        LocalDateTime reservationDateTime = reservation.reservationDateTime();
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservationDateTime.toLocalDate().toString(),
                reservationDateTime.toLocalTime().toString()
        );
    }
}
