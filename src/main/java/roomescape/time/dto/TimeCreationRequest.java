package roomescape.time.dto;

import roomescape.time.model.Time;

import java.time.LocalTime;

public class TimeCreationRequest {
    public String time;

    public static Time toEntityFrom(TimeCreationRequest request) {
        return new Time(LocalTime.parse(request.time));
    }
}