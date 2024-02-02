package roomescape.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public class CreateTimeDto {

    @NotEmpty
    String time;

    public LocalTime getTime() {
        return LocalTime.parse(time);
    }
}
