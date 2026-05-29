package roomescape.time.dto;

import jakarta.validation.constraints.NotBlank;

public record TimeCreateRequest(
        @NotBlank (message = "시간은 필수입니다.")
        String time) {
}
