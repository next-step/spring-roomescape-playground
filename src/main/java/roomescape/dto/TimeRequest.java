package roomescape.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record TimeRequest(
        @NotNull(message = "등록할 시간을 입력해주세요.")
        LocalTime time
) {
}
