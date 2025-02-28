package roomescape.dto.reservation.request;

import java.time.LocalDate;

public record ReservationRequest(
    String name,
    LocalDate date,
    Long time
) {}
