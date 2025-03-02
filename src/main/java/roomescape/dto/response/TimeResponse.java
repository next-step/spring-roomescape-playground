package roomescape.dto.response;

import roomescape.domain.Time;

import java.time.LocalTime;

public record TimeResponse(
        long id,
        LocalTime time
) {
    public TimeResponse(final Time time) {
        this(time.getId(),
                time.getTime());
    }
}
