package roomescape.reservation.dto;

import roomescape.reservation.domain.Reservation;

public record ReservationResponse(Long id, String name, String date, String time) {
    public static ReservationResponse fromReservation(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate().toString(),
                reservation.getTime().getValue().toString());
    }
}
