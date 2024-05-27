package roomescape.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDTO(
        String name,
        @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
        LocalTime time
) {
}