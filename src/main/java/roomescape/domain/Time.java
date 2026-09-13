package roomescape.domain;

import java.time.LocalTime;

public class Time {

    private final long id;
    private final LocalTime time;

    public Time(long id, LocalTime time) {

        if (time == null) {
            throw new IllegalArgumentException();
        }

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
