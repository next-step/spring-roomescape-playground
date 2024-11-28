package roomescape.entity;

import java.time.LocalTime;

public class Time {
    public Long id;
    public String time;

    public Time(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public Time(String time) {
        this(null, time);
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public LocalTime getTimeASALocalTime(){ //string->LocalTime
        return LocalTime.parse(time);
    }
}
