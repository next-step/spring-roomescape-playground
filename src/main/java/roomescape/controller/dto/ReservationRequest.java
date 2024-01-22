package roomescape.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.domain.Reservation;

import java.time.LocalDate;

public record ReservationRequest(
        Long id,
        @NotBlank(message = "이름은 공백일 수 없습니다") String name,
        @NotNull(message = "날짜를 입력해야 합니다") LocalDate date,
        @NotBlank(message = "시간을 입력해야 합니다") String time
) {

    public Reservation toEntity() {
        return new Reservation(id, name, date, time);
    }
}
