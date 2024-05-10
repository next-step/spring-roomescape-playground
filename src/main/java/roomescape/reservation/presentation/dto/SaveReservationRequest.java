package roomescape.reservation.presentation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import roomescape.reservation.domain.Reservation;

public record SaveReservationRequest(
        String name,
        String date,
        String time
) {

    public Reservation toReservation() {
        LocalDateTime reservationDateTime = LocalDateTime.of(
                LocalDate.parse(date),
                LocalTime.parse(time)
        );
        return new Reservation(name, reservationDateTime);
    }
}
