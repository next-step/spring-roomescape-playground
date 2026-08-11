package roomescape;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,

        @NotBlank
        @Pattern(regexp = "^[가-힣a-zA-Z]+( [가-힣a-zA-Z]+)*$")
        String name,

        @NotNull
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {
}
