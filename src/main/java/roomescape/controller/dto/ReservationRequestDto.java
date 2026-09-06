package roomescape.controller.dto;

import roomescape.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationRequestDto(
        String name,
        LocalDate date,
        LocalTime time
) {
    public Reservation toEntity() {
        return Reservation.create(name, date, time);
    }
}


