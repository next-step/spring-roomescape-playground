package roomescape.controller.dto;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalTime;

public class CreateTimeDto {

    @NotEmpty
    String time;

    public LocalTime getTime() {
        return LocalTime.parse(time);
    }
}
