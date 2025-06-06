package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDTO(
        String name,
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time) {
}
