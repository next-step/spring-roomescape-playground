package roomescape.domain;

import java.time.LocalTime;

public class Time {

    private final Long id;
    private final LocalTime time;

    private Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static Time from(LocalTime time) {
        return new Time(null, time);
    }

    public static Time withId(Long id, Time time) {
        return new Time(id, time.time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
