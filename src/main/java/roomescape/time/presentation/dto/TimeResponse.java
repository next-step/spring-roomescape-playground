package roomescape.time.presentation.dto;

import roomescape.time.domain.Time;

public record TimeResponse(Long id,
                           String time) {

    public static TimeResponse from(final Time time) {
        return new TimeResponse(time.id(), time.time());
    }
}
