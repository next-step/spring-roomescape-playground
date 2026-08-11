package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequest(
        String name,
        String date,
        String time
) {
    public Reservation toReservation() {
        return new Reservation(
                name,
                LocalDate.parse(date),
                LocalTime.parse(time)
        );
    }
}
