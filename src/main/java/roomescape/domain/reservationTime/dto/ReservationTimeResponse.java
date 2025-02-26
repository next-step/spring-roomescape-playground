package roomescape.domain.reservationTime.dto;

import java.time.LocalTime;
import roomescape.domain.reservationTime.domain.ReservationTime;

public record ReservationTimeResponse(Long id, LocalTime time) {

    public static ReservationTimeResponse fromReservationTime(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getTime());
    }
}
