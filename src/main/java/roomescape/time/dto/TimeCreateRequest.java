package roomescape.time.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TimeCreateRequest(
        @NotBlank @NotNull(message = "시간은 필수입니다.")
        String time) {
}
