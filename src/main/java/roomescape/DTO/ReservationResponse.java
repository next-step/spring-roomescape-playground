package roomescape.DTO;

import roomescape.Model.Reservation;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        String name,
        String date,
        String time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
