package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.time.dto.TimeResponse;

public record ReservationCreateResponse(
        Long id,
        String name,
        String date,
        TimeResponse time
)
{
    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(
                reservation.getId(),
                reservation.getName(),
                String.valueOf(reservation.getDate()),
                TimeResponse.from(reservation.getTime())
        );
    }
}
