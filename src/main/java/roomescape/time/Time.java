package roomescape.time;

import java.time.LocalTime;

public class Time {

    private Long id;

    private LocalTime time;

    private Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static Time ofNew(LocalTime time) {
        return new Time(null, time);
    }

    public static Time ofExist(Long id, LocalTime time) {
        return new Time(id, time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
