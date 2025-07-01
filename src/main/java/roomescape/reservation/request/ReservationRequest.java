package roomescape.reservation.request;

import java.time.LocalDate;

public record ReservationRequest(
    LocalDate date,
    String name,
    Long time
) { }
