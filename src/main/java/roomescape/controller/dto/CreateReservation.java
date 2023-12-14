package roomescape.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record CreateReservation(
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate date,
    @NotBlank
    String name,
    @NotNull
    Long time
) {

}
