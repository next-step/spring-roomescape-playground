package roomescape.dto;

import roomescape.model.Reservation;

public record ReservationCreateResponse(int id, String name, String date, String time) {
    public static ReservationCreateResponse from(Reservation reservation) {
        return new ReservationCreateResponse(reservation.id(), reservation.name(), reservation.date(), reservation.time());
    }
}

