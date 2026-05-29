package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotNull(message = "날짜는 필수입니다.")
        LocalDate date,

        @NotNull(message = "시간은 필수입니다.")
        LocalTime time
) {
}
