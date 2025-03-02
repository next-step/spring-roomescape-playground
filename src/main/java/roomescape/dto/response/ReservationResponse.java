package roomescape.dto.response;

import roomescape.domain.Reservation;

import java.time.LocalDate;

public record ReservationResponse(
        long id,

        String name,

        LocalDate date,

        TimeResponse time
) {
    public ReservationResponse(final Reservation reservation) {
        this(reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                new TimeResponse(reservation.getTime()));
    }
}
