package roomescape.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotBlank(message = "이름은 비어 있을 수 없습니다.")
        String name,

        @NotNull(message = "날짜는 비어 있을 수 없습니다.")
        @FutureOrPresent(message = "예약 날짜는 오늘 또는 미래여야 합니다.")
        LocalDate date,

        @NotNull(message = "시간은 비어 있을 수 없습니다.")
        LocalTime time
) {
}