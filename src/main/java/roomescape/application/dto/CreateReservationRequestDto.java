package roomescape.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservedDateTime;

public record CreateReservationRequestDto(
        @NotBlank(message = "이름은 필수 입력값 입니다.") String name,
        @NotNull(message = "예약날짜는 필수 입력값 입니다.") LocalDate date,
        @NotNull(message = "예약시간은 필수 입력값 입니다.") LocalTime time
) {

    public Reservation toReservation() {
        return new Reservation(null, name, new ReservedDateTime(date, time));
    }
}
