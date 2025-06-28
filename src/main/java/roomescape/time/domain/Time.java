package roomescape.time.domain;

import roomescape.time.dto.TimeRequest;

import java.time.LocalTime;

public record Time(Long id, LocalTime time) {
    public static Time of(TimeRequest request) {
        return new Time(null, request.time());
    }
}
