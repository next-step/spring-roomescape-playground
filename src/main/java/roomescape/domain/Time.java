package roomescape.domain;

import java.time.LocalTime;

public class Time {

    private final Long id;
    private final LocalTime timeValue;

    public Time(Long id, LocalTime timeValue) {
        this.id = id;
        this.timeValue = timeValue;
    }

    public static Time from(LocalTime time) {
        return new Time(null, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTimeValue() {
        return timeValue;
    }
}
