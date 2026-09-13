package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        TimeResponse time
) {
    public static ReservationResponse convert(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                TimeResponse.convert(reservation.time())
        );
    }
}
