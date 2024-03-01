package roomescape.controller.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationCreate(
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date,
    @NotBlank
    String name,
    @NotNull
    Long time
) {

}
