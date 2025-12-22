package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public record TimeRequest(
        @NotBlank(message = "시간은 비어있을 수 없습니다.")
        String time
) {
}

