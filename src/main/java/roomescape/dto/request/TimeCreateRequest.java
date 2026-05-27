package roomescape.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeCreateRequest {
    @NotNull(message = "날짜는 필수 입력값입니다.")
    private LocalTime time;
}
