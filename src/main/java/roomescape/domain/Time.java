package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private long id;
    private LocalTime time;

    public Time(long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
