package roomescape;

import java.time.LocalTime;
import roomescape.exception.InvalidReservationException;

public class Time {

    private static final long UNSAVED_ID = 0L;
    private final long id;

    private final LocalTime time;

    public static Time create(LocalTime time) {
        return new Time(UNSAVED_ID, time);
    }

    public static Time create(long id, LocalTime time) {
        return new Time(id, time);
    }

    private Time(long id, LocalTime time) {
        validate(time);
        this.id = id;
        this.time = time;
    }

    private static void validate(LocalTime time) {
        if (time == null) {
            throw new InvalidReservationException("시간은 비어있을 수 없습니다.");
        }
    }

    public Time withId(long id) {
        return new Time(id, time);
    }

    @Override
    public String toString() {
        return "Time{" +
            "id=" + id +
            ", time='" + time + '\'' +
            '}';
    }

    public LocalTime getTime() {
        return time;
    }

    public long getId() {
        return id;
    }
}
