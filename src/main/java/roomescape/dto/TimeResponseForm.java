package roomescape.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public class TimeResponseForm {
    Long id;
    LocalTime time;

    public TimeResponseForm() {
    }
    public TimeResponseForm(Time time) {
        this.id = id;
        this.time = time.getTime();
    }
    public Long getId() {
        return id;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
}
