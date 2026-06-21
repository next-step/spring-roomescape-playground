package roomescape.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;

public record TimeDto(
        @Nullable
        Long id,

        @NotBlank(message = "시간은 비어있으면 안됩니다")
        String time
) {
}
