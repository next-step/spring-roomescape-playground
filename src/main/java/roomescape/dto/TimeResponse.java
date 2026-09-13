package roomescape.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public class TimeResponse {

    private final long id;
    private final LocalTime time;

    public TimeResponse(long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static TimeResponse convert(Time time) {
        return new TimeResponse(
                time.getId(),
                time.getTime()
        );
    }

    public long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
