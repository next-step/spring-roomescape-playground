package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReservationDto(
        @NotBlank(message = "이름은 비어있으면 안됩니다")
        String name,

        @NotBlank(message = "날짜는 비어있으면 안됩니다")
        String date,

        @NotNull(message = "시간은 비어있으면 안됩니다")
        Long time_id) {

}
