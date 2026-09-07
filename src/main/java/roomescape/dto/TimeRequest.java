package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record TimeRequest(
        @NotNull(message = "시간대는 비어 있을 수 없습니다.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
}
