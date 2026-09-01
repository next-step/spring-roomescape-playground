package roomescape.dto;

import java.time.LocalTime;
import roomescape.domain.ReservationTime;

public record TimeRequest(LocalTime time) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(null, time);
    }
}
