package roomescape.domain.reservationTime.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import roomescape.domain.reservationTime.domain.ReservationTime;

public record ReservationTimeRequest(@NotNull LocalTime time) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(time);
    }
}
