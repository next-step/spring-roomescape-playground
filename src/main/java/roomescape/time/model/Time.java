package roomescape.time.model;

import java.time.LocalTime;

public class Time {

    private final Long id;
    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
