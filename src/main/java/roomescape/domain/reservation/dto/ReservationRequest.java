package roomescape.domain.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservationTime.domain.ReservationTime;

public record ReservationRequest(
        @NotNull(message = "이름은 필수 입력값입니다.")
        @NotBlank(message = "이름은 비어 있을 수 없습니다.")
        @Size(max = 255, message = "이름은 255자 이하여야 합니다.")
        String name,

        @NotNull(message = "예약 날짜는 필수 입력값입니다.")
        LocalDate date,

        @NotNull(message = "예약 시간은 필수 입력값입니다.")
        Long time
) {
    public Reservation toReservation(final ReservationTime reservationTime) {
        return new Reservation(this.name(), this.date(), reservationTime);
    }
}
