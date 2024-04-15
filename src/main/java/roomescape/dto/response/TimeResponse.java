package roomescape.dto.response;

import roomescape.domain.Time;

public class TimeResponse {

    private final Long id;
    private final String time;

    public TimeResponse(Long id, String time) {
        this.id = id;
        this.time = time;
    }

    public static TimeResponse from(Time time){
        return new TimeResponse(time.getId(), time.getTime());
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
