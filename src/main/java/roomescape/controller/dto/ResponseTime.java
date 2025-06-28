package roomescape.controller.dto;

import roomescape.domain.Time;

public record ResponseTime(
        Long id,
        String time
) {

    public static ResponseTime from(final Time time) {
        return new ResponseTime(time.id(), time.time());
    }
}
