package roomescape.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        Long id,
        @NotBlank(message = "이름은 공백일 수 없습니다")
        String name,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        @NotNull(message = "날짜를 입력해야 합니다")
        LocalDate date,
        @DateTimeFormat(pattern = "HH:mm")
        @NotNull(message = "시간을 입력해야 합니다")
        LocalTime time
) {

    public Reservation toEntity() {
        return new Reservation(id, name.toString(), date, time);
    }
}
