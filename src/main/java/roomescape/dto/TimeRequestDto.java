package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public class TimeRequestDto {

    @NotBlank
    private String time;

    public TimeRequestDto() {
    }

    public TimeRequestDto(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
