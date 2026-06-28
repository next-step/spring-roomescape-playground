package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import roomescape.domain.Time;

public record ReservationRequest(
        Long id,
        @NotBlank(message = "ERROR:이름을 입력하시오.")
        String name,
        @NotBlank(message = "ERROR:날짜를 입력하시오.")
        String date,
        Time time
) {
}
