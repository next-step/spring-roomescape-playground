package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public class TimeRequestDto {

    @NotBlank(message = "시간을 입력해 주세요.")
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
