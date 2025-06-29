package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.Time;

public record ResponseTime(
        Long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {

    public static ResponseTime from(final Time time) {
        return new ResponseTime(time.id(), time.time());
    }
}
