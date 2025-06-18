package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public record RequestReservation(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date,
        String name,
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime time
) {
}
