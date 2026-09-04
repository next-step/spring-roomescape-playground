package roomescape.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        @NotBlank(message = "예약자 이름은 비워둘 수 없습니다.")
        String name,

        @NotNull(message = "예약 날짜는 비워둘 수 없습니다.")
        LocalDate date,

        @NotNull(message = "예약 시간은 비워둘 수 없습니다.")
        LocalTime time) {
}
