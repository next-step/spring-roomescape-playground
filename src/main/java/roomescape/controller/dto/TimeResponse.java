package roomescape.controller.dto;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import roomescape.domain.Time;

public record TimeResponse(
    long id,
    @JsonFormat(pattern = "HH:mm") LocalTime time // 0 ~ 23시간 형식
) {
    public static TimeResponse from(Time time) {
        return new TimeResponse(
            time.getId(),
            time.getTime()
        );
    }
}
