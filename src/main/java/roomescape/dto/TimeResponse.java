package roomescape.dto;

import roomescape.domain.Time;
import java.time.LocalTime;

public class TimeResponse {

    private Long id;
    private LocalTime time;

    public TimeResponse(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static TimeResponse from(Time time) {
        return new TimeResponse(time.getId(), time.getTime());
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}