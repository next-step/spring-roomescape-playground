package roomescape.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import roomescape.domain.Time;

public class TimeResponse {
    private final Long id;
    @DateTimeFormat(pattern = "HH:MM")
    private final LocalTime time;

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
