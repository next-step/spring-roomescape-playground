package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeResponse(
    Long id,
    @JsonFormat(pattern = "HH:mm")
    LocalTime time
) {

    public static TimeResponse from(Time time) {
        return new TimeResponse(
            time.getId(),
            time.getTime()
        );
    }
}
