package roomescape.dto;

import java.time.LocalTime;
import roomescape.model.Time;

public record TimeCreateResponse(int id, LocalTime time) {
    public static TimeCreateResponse from(Time time) {
        return new TimeCreateResponse(time.id(), time.value());
    }
}

