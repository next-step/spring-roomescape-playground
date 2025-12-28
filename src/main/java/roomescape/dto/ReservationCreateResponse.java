package roomescape.dto;

import java.time.LocalDate;
import roomescape.model.Reservation;
import roomescape.model.Time;

public record ReservationCreateResponse(int id, String name, LocalDate date, Time time) {
    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(reservation.id(), reservation.name(), reservation.date(), reservation.time());
    }
}

