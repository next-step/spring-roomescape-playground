package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.Time;

public record ReservationTimeResponse(
    Long id,
    @JsonFormat(pattern = "hh:mm")
    LocalTime time
) {

    public static ReservationTimeResponse from(Time time) {
        return new ReservationTimeResponse(
            time.getId(),
            time.getTimeValue()
        );
    }
}
