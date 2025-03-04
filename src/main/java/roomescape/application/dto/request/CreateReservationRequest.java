package roomescape.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.ReservedDateTime;
import roomescape.domain.time.Time;

public record CreateReservationRequest(
        @NotBlank(message = "이름은 필수 입력값 입니다.") String name,
        @NotNull(message = "예약날짜는 필수 입력값 입니다.") LocalDate date,
        @NotNull Long time
) {

    public Reservation toReservation(Time time) {
        return new Reservation(null, name, new ReservedDateTime(date, time));
    }
}
