package roomescape.domain.reservationTime.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.domain.reservationTime.domain.ReservationTime;

public record ReservationTimeRequest(@NotNull(message = "예약 시간은 필수 입력값입니다.") LocalTime time) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(time);
    }
}
