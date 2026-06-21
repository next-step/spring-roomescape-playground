package roomescape.dto;

import roomescape.domain.Reservation;

import java.time.LocalTime;

public record ReservationResponse(Long id, String name, String date, LocalTime time) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(), reservation.getTime().getTime());
    }
}
