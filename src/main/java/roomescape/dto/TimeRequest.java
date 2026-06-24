package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public record TimeRequest(
        Long id,
        @NotBlank(message = "time is blank")
        String time
) {
}
