package roomescape.dto;

import roomescape.domain.Time;

public class TimeResponse {
    private Long id;
    private String time;

    public TimeResponse(Time time) {
        this.id = time.getId();
        this.time = time.getTime();
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
