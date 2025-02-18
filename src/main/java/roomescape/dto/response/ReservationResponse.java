package roomescape.dto.response;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        long id,

        String name,

        LocalDate date,

        LocalTime time
) {
    public ReservationResponse(Reservation reservation) {
        this(reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
