package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotBlank String name,
        @NotNull LocalDate date,
        @NotNull LocalTime time) {
}
