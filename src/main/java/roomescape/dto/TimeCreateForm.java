package roomescape.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public class TimeCreateForm {

    private LocalTime time;

    public TimeCreateForm() {
    }
    public Time toEntity() {
        return new Time(null, this.time);
    }
    public LocalTime getTime() {
        return time;
    }
    public LocalTime setTime(LocalTime time) {
        return this.time = time;
    }
}

