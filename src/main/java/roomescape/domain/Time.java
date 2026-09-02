package roomescape.domain;

import java.time.LocalTime;

public class Time {

    private Long id;
    private LocalTime time;

    private Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Time(LocalTime time) {
        this(null, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }

    public Time withId(Long id) {
        return new Time(id, time);
    }
}
