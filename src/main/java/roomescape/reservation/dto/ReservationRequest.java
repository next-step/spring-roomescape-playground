package roomescape.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.reservation.domain.Reservation;

public record ReservationRequest(String name, LocalDate date, LocalTime time) {

    public static Reservation toReservation(final ReservationRequest reservationRequest, Long id) {
        return new Reservation(id, reservationRequest.name(), reservationRequest.date(), reservationRequest.time());
    }
}
