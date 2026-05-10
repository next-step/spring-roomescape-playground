package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservationDto(
        @NotBlank
        String name,

        @NotBlank
        String date,

        @NotBlank
        String time) {
}
