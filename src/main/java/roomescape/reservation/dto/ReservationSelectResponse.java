package roomescape.reservation.dto;

import roomescape.time.dto.TimeResponse;

public record ReservationSelectResponse (
        Long id,
        String name,
        String date,
        TimeResponse time
)
{ }
