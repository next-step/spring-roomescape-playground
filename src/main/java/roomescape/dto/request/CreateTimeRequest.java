package roomescape.dto.request;

import roomescape.domain.Time;

import java.time.LocalTime;

public record CreateTimeRequest(
        LocalTime time
) {
    public Time toTime() {
        return new Time(time);
    }
}
