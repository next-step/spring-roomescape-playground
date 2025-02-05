package roomescape.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequest(
        String name,

        LocalDate date,

        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
}
