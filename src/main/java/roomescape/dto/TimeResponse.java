package roomescape.dto;

import roomescape.domain.Times;

import java.time.LocalTime;

public record TimeResponse(
        LocalTime time
) {
    public static TimeResponse from(Times times) {
        return new TimeResponse(times.getTime());
    }
}
