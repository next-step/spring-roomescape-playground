package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import roomescape.validation.ValidDate;

public record ReservationCreateRequest(
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        String name,

        @ValidDate(message = "날짜 형식이 올바르지 않습니다. 예: yyyy-MM-dd")
        String date,

        @Pattern(regexp = "^\\d+$", message = "시간 ID 형식이 올바르지 않습니다.")
        String time
) { }
