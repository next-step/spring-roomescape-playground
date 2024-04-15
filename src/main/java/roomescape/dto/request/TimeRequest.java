package roomescape.dto.request;

import jakarta.validation.constraints.NotEmpty;

public class TimeRequest {

    @NotEmpty(message = "시간은 공백이 아니어야 합니다.")
    private String time;

    public String getTime() {
        return time;
    }
}