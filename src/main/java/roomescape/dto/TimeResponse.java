package roomescape.dto;

import roomescape.domain.Time;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;

public record TimeResponse(long id, @JsonFormat(pattern = "HH:mm") LocalTime time) {
    public static TimeResponse convert(Time time) {
        return new TimeResponse(
                time.getId(),
                time.getTime()
        );
    }
}
