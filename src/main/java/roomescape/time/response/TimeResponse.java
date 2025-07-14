package roomescape.time.response;

import roomescape.time.model.Time;
import roomescape.util.DateTimeUtil;

public record TimeResponse(
    Long id,
    String time
) {

    public TimeResponse(Time time) {
        this(time.getId(), DateTimeUtil.format(time.getTime()));
    }

    public static TimeResponse of(Time time) {
        return new TimeResponse(time);
    }
}
