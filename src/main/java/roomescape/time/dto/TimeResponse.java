package roomescape.time.dto;

import roomescape.time.model.Time;

public class TimeResponse {
    public Long id;
    public String time;

    public TimeResponse() {}

    public static TimeResponse from(Time time) {
        TimeResponse response = new TimeResponse();
        response.id = time.getId();
        response.time = time.getTime().toString();

        return response;
    }
}