package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationCreateRequest(
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        String name,

        @NotNull(message = "날짜는 반드시 입력해야 합니다.")
        LocalDate date,

        @NotNull(message = "시간은 반드시 입력해야 합니다.")
        LocalTime time
) { }
