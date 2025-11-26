package roomescape.model;

import java.time.LocalTime;

public class Time {
    Long id;
    LocalTime time;

    private Time(Long id, LocalTime time) {
        this.id = id;
        this.time = time;
    }

    public static Time from (LocalTime time)
    {
        return new Time(null,time);
    }

    public static Time of (Long id, LocalTime time){
        return new Time(id,time);
    }

    public Long getId() {
        return id;
    }

    public LocalTime getTime() {
        return time;
    }
}
