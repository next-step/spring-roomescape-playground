package roomescape.dto;

import roomescape.domain.Time;

import java.time.LocalTime;

public record TimeRequest(
        LocalTime time
) {
    public Time toEntity() {
        return new Time(null, time);
    }
}
