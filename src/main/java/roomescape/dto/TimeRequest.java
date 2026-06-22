package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalTime;

public class TimeRequest {
    @NotNull(message = "시간은 필수 입력 값입니다.")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    public TimeRequest() {}

    public TimeRequest(LocalTime time) { this.time = time; }

    public LocalTime getTime() { return time; }
}
