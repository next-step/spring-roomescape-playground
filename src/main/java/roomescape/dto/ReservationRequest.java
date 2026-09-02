package roomescape.dto;

import java.time.LocalDate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public record ReservationRequest(
        String name,
        LocalDate date,
        Long time
) {

    public Reservation toReservation(ReservationTime reservationTime) {
        return new Reservation(null, name, date, reservationTime);
    }
}
