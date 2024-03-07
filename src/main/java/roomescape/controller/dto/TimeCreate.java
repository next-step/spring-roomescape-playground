package roomescape.controller.dto;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import roomescape.domain.Time;

public record TimeCreate(
    @NotNull
    @DateTimeFormat(pattern = "HH:mm")
    LocalTime time
) {
    public Time toTime() {
        return new Time(null, time);
    }
}
