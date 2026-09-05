package roomescape.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;

public record ReservationRequest (
    String name,
    LocalDate date,
    Long time
) {
    public Reservation toEntity(Time reservationTime) {
        return Reservation.create(name, date, reservationTime);
    }
}
