package roomescape.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public class TimeResponseForm {

    private Long id;
    private String time;

    public TimeResponseForm(Time time) {
        this.id = time.getId();
        this.time = time.getTime();
    }

    public TimeResponseForm() {
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
