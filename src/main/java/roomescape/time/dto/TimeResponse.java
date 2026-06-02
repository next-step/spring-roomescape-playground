package roomescape.time.dto;

import roomescape.time.domain.Time;

public record TimeResponse (
        Long id,
        String time
) {

    public static TimeResponse from(Time time) {
        return new TimeResponse(
                time.getId(),
                String.valueOf(time.getValue())
        );
    }
}
