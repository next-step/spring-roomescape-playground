package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.model.Reservation;

public record ReservationCreateResponse(int id, String name, LocalDate date, LocalTime time) {
    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(reservation.id(), reservation.name(), reservation.date(), reservation.time());
    }
}

