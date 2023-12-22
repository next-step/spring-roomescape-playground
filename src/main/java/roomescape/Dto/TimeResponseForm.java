package roomescape.Dto;

import roomescape.Domain.Time;

public class TimeResponseForm {

    private final Long id;
    private final String time;

    public TimeResponseForm(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static TimeResponseForm from(Time time) {
        return new TimeResponseForm(time.getId(), time.getTime());
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
