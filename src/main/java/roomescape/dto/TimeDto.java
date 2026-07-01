package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public record TimeDto(
        @NotBlank(message = "시간은 비어있으면 안됩니다")
        String time
) {
}
