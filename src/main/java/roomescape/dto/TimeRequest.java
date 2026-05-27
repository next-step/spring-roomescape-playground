package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public class TimeRequest {

    @NotBlank(message = "시간은 필수입니다.")
    private String time;

    public TimeRequest() {
    }

    public String getTime() { return time; }
}
