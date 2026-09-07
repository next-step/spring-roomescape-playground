package roomescape.domain;

import roomescape.exception.InvalidTimeException;

import java.time.LocalTime;

public class Time {

    private final Long id;
    private final LocalTime time;

    private Time(Long id, LocalTime time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    public Time(LocalTime time) {
        this(null, time);
    }

    private void validate(LocalTime time) {
        if (time == null) {
            throw new InvalidTimeException("시간 값은 필수입니다.");
        }
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
