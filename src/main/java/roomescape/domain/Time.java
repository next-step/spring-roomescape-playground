package roomescape.domain;

import java.time.LocalTime;

public record Time(
        Long id,
        LocalTime time
) {

    public static Time of(final Long id, final LocalTime time) {
        return new Time(id, time);
    }
}
