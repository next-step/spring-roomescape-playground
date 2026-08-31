package com.cholog.roomescape.roomescape.dto.response;

import com.cholog.roomescape.roomescape.entity.Time;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;


public record TimeResponse(
        Long id,

        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
    public static TimeResponse toDto(Time time) {
        return new  TimeResponse(time.getId(), time.getTime());
    }
}
