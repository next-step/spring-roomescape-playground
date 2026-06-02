package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;
import roomescape.time.dto.TimeResponse;

public record ReservationSelectResponse (
        Long id,
        String name,
        String date,
        TimeResponse time
)
{
    public static ReservationSelectResponse from(Reservation reservation) {
        return new ReservationSelectResponse(
                reservation.getId(),
                reservation.getName(),
                String.valueOf(reservation.getDate()),
                TimeResponse.from(reservation.getTime())
        );
    }
}
