package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateCommand(
        String name,
        LocalDate date,
        LocalTime time
) {
}
