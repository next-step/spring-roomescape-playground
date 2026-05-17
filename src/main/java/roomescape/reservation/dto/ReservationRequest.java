package roomescape.reservation.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.validation.constraints.NotBlank;
import roomescape.reservation.domain.Reservation;

public record ReservationRequest(
    @NotBlank(message = "이름은 비어 있을 수 없습니다.")
    @Size(max = 255, message = "이름은 255자를 넘을 수 없습니다.")
    String name,

    @NotNull(message = "날짜는 필수입니다.")
    @Future(message = "예약 날짜는 미래여야 합니다.")
    LocalDate date,

    @NotNull(message = "시간은 필수입니다.")
    LocalTime time
) {
    public Reservation toReservation() {
        return new Reservation(null, name, date, time);
    }
}
