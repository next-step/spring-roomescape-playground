package roomescape.dto.response;

import roomescape.domain.Time;

public record TimeResponse(
        long id,
        String time
) {
    public TimeResponse(Time time) {
        this(time.getId(),
                time.getTime());
    }
}
