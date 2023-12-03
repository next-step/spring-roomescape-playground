package roomescape.application.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public class TimeCreateRequest {

    private String time;

    public TimeCreateRequest() {
    }

    public TimeCreateRequest(final String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public static Time from(final TimeCreateRequest request) {
        return new Time(LocalTime.parse(request.time));
    }
}
