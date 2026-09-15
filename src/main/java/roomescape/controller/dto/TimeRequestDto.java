package roomescape.controller.dto;

import roomescape.model.Time;

import java.time.LocalTime;

public record TimeRequestDto(
        LocalTime time
) {
    public Time toEntity() {
        return Time.create(time);
    }
}
