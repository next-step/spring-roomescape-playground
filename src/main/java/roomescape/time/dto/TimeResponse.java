package roomescape.time.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.time.domain.Time;

import java.time.LocalTime;

public record TimeResponse(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss") LocalTime time
) {
    public static TimeResponse from(Time time) {
        return new TimeResponse(time.id(), time.time());
    }
}
