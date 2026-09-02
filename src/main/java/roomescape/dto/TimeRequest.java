package roomescape.dto;

import roomescape.domain.Time;

public record TimeRequest(
        String time
) {
    public Time toEntity() {
        return new Time(null, time);
    }
}
