package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(
        @NotBlank String name,
        @NotBlank String date,
        @NotNull Long timeId
) { }
