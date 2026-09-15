package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.model.Time;

import java.time.LocalTime;

public record TimeResponseDto(
        Long id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        LocalTime time
) {
    public TimeResponseDto(Time time) {
        this(
                time.getId(),
                time.getTime()
        );
    }
}
