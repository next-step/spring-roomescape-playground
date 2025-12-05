package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(
        @NotBlank(message = "이름은 필수 항목 입니다.")
        String name,
        @NotBlank(message = "날짜는 필수 항목입니다.")
        String date,
        @NotNull(message = "시간은 필수 항목입니다.")
        Long timeId
) {
}
