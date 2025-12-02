package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record ReservationRequest(
        @NotBlank String name,
        @Pattern(
                regexp = "^\\d{4}-\\d{2}-\\d{2}$",
                message = "날짜는 yyyy-MM-dd 형식이어야 합니다. 예) 2023-01-01"
        )
        @NotNull Long timeId
) { }
