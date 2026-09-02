package roomescape.domain.time;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public class TimeRequest {
    @NotNull(message = "시간을 입력해주세요.")
    private LocalTime time;

    public TimeRequest() {}

    public LocalTime getTime() { return time; }
}
