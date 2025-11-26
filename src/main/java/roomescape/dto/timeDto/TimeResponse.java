package roomescape.dto.timeDto;

import roomescape.model.Time;

import java.time.LocalTime;

public record TimeResponse (
        Long id,
        LocalTime time
){
    public static TimeResponse from (Time time) {
        return new TimeResponse(
                time.getId(),
                time.getTime()
        );
    }
}
