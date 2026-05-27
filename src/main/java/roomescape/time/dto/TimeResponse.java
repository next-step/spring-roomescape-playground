package roomescape.time.dto;

import java.time.LocalTime;
import roomescape.time.domain.Time;

public record TimeResponse(Long id, LocalTime time) {
    public static TimeResponse fromTime(Time time) {
        return new TimeResponse(time.getId(), time.getValue());
    }
}
