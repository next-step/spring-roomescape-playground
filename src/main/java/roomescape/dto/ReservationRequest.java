package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservationRequest(
        @NotBlank(message = "예약자 이름은 비어있을 수 없습니다.")
        String name,

        @NotNull(message = "예약 날짜를 입력해주세요.")
        LocalDate date,

        @NotNull(message = "예약 시간을 입력해주세요.")
        Long time
) {
}
