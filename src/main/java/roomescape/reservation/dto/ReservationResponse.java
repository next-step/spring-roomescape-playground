package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.time.dto.TimeResponse;

import java.time.LocalDate;


public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,
        TimeResponse time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                TimeResponse.from(reservation.time())
        );
    }
}
