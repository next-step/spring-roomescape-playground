package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.validation.ValidDate;
import roomescape.validation.ValidTime;

public record ReservationCreateRequest(
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        String name,

        @ValidDate(message = "날짜 형식이 올바르지 않습니다. 예: yyyy-MM-dd")
        String date,

        @ValidTime(message = "시간 형식이 올바르지 않습니다. 예: HH:mm")
        String time
) { }
