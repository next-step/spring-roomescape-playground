package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.time.domain.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponseDto(
        long id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
        Time time
) {
}
