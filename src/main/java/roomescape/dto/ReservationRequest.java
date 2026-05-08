package roomescape.dto;

import jakarta.validation.constraints.NotBlank;

public record ReservationRequest(
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "날짜는 필수입니다.")
        String date,

        @NotBlank(message = "시간은 필수입니다.")
        String time
) {
}
