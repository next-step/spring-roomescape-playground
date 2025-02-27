package roomescape.dto.request;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

import java.time.LocalDate;

public record CreateReservationRequest(
        String name,

        LocalDate date,

        long time
) {
    public Reservation toReservation(final Time time) {
        return new Reservation(
                name,
                date,
                time
        );
    }
}
