package roomescape.dto.reservation.response;

import java.time.LocalDate;
import roomescape.domain.Time;
import roomescape.dto.time.response.TimeResponse;

public record ReservationResponse(
    Long id,
    String name,
    LocalDate date,
    TimeResponse time
) {}
