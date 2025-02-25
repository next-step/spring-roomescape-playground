package roomescape.dto.request;

import roomescape.domain.Time;

public record CreateTimeRequest(
        String time
) {
    public Time toTime() {
        return new Time(time);
    }
}
