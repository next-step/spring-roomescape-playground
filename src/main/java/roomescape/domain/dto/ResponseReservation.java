package roomescape.domain.dto;

import roomescape.domain.Reservation;

public record ResponseReservation(
        Long id,
        String name,
        String date,
        String time
) {
    public static ResponseReservation from(Reservation reservation) {
        return new ResponseReservation(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
