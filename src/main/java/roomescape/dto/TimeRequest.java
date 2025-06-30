package roomescape.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record TimeRequest(
    @NotNull(message = "시간은 비어있을 수 없습니다.")
    LocalTime time
) {
}
