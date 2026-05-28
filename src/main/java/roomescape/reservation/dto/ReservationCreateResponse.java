package roomescape.reservation.dto;

import java.sql.Date;

public record ReservationCreateResponse(
        Long id,
        String name,
        Date date,
        Long time
)
{ }
