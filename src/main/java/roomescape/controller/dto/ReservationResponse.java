package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import roomescape.domain.Reservation;

public record ReservationResponse(long id, String name, LocalDate date, LocalTime time) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime()
        );
    }
}
