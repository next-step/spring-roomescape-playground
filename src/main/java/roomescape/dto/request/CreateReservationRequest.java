package roomescape.dto.request;

import roomescape.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationRequest(
        String customerName,

        LocalDate date,

        LocalTime time
) {
    public Reservation toReservation() {
        return new Reservation(
                customerName,
                date,
                time
        );
    }
}
