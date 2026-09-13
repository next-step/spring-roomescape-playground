package roomescape.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public record TimeResponse(long id, LocalTime time) {
    public static TimeResponse convert(Time time) {
        return new TimeResponse(
                time.getId(),
                time.getTime()
        );
    }
}
