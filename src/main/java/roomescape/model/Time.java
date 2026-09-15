package roomescape.model;

import java.time.LocalTime;

public class Time {
    private final Long id;
    private final LocalTime time;

    public Time(Long id, LocalTime time) {
        validateTimeArgument(time);
        this.id = id;
        this.time = time;
    }

    public static Time create(LocalTime time) {
        return new Time(null, time);
    }

    public static Time restore(Long id, LocalTime time) {
        return new Time(id, time);
    }

    public Time withId(Long id) {
        return new Time(id, this.time);
    }

    public void validateTimeArgument(LocalTime time) {
        if (time == null) {
            throw new IllegalArgumentException("시간은 비워져있을 수 없습니다.");
        }
    }

    public Long getId(){
        return id;
    }
    public LocalTime getTime() {
        return time;
    }
}
