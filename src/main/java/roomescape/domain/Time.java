package roomescape.domain;

import java.time.LocalTime;

public class Time {
    private final Long id;
    private final LocalTime time;

    public Time(final Long id, final LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Time(final Long id) {
        this(id, null);
    }

    public Time(final LocalTime time) {
        this(null, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
