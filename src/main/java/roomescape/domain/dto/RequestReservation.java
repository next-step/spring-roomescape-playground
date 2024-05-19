package roomescape.domain.dto;

import roomescape.domain.Reservation;

public record RequestReservation(
        String name,
        String date,
        String time
) {
    public Reservation toReservation() {
        return new Reservation(name, date, time);
    }
}
