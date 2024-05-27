package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalTime;
import roomescape.domain.Time;

public record TimeRequest(
        Long id,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
    public static TimeRequest from(Time time) {
        return new TimeRequest(time.getId(), time.getTime());
    }
}