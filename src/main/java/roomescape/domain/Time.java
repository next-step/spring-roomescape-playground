package roomescape.domain;

import roomescape.exception.BlankReservationException;

import java.time.LocalTime;

public class Time {
    private final long id;
    private final LocalTime time;

    public Time(LocalTime time) {
        this(0, time);
    }

    public Time(long id, LocalTime time) {
        validateTime(time);
        this.id = id;
        this.time = time;
    }

    private static void validateTime(LocalTime time) {
        if (time == null) {
            throw new BlankReservationException("시간을 입력해주세요.");
        }
    }

    public Time withId(long id) {
        return new Time(id, this.time);
    }

    public long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
