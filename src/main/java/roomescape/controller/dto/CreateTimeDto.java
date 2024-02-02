package roomescape.controller.dto;

import jakarta.validation.constraints.NotEmpty;

public class CreateTimeDto {

    @NotEmpty
    String time;

    public String getTime() {
        return time;
    }
}
