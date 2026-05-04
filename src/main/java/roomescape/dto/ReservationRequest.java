package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotBlank(message = "이름은 비어있을 수 없습니다.")
        String name,

        @NotNull(message = "날짜를 입력해주세요.")
        LocalDate date,

        @NotNull(message = "시간을 입력해주세요.")
        LocalTime time) {
}
