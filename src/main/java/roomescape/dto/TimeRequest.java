package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record TimeRequest(
        @NotNull(message = "시간은 필수 입력값입니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
}
