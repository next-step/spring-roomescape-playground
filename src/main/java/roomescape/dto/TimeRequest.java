package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import roomescape.domain.Time;

public class TimeRequest {

    @NotBlank(message = "시간은 필수입니다.")
    private String time;

    public TimeRequest() {
    }

    public TimeRequest(String time) {
        this.time = time;
    }

    public Time toEntity() {
        return new Time(null, time);
    }

    public String getTime() {
        return time;
    }
}
