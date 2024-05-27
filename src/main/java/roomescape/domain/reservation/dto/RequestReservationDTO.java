package roomescape.domain.reservation.dto;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.Time;

public record RequestReservationDTO(String name,
                                    String date,
                                    long time) {

    public Reservation toReservaiton() {
        return new Reservation(
                name,
                date,
                new Time(time));
    }
}
