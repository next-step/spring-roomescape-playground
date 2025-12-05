package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public record TimeRequest(
        @NotBlank String time
) { }