package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservationRequest(
        Long id,
        @NotBlank(message = "ERROR:이름을 입력하시오.")
        String name,
        @NotBlank(message = "ERROR:날짜를 입력하시오.")
        String date,
        @NotBlank(message = "ERROR:시간을 입력하시오.")
        String time
) {
}
