package roomescape.time.domain;

import java.time.LocalTime;

public class Time {
    private Long id;
    private LocalTime time;

    public Time(LocalTime time) {
        this.time = time;
    }

    private Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public Time withId(Long id) {
        return new Time(id, this.time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
