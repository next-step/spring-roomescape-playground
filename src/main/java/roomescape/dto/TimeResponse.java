package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.Time;

public class TimeResponse {
    private final Long id;
    private final LocalTime time;

    public TimeResponse(final Long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public TimeResponse(final Time time) {
        this.id = time.getId();
        this.time = time.getTime();
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
