package roomescape.domain.reservation;

import roomescape.domain.reservation.dto.ReservationDTO;

public record ResponseReservation(long id,
                                  String name,
                                  String date,
                                  String time) {
    public static ResponseReservation toResponseReservation(ReservationDTO reservation) {
        return new ResponseReservation(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time().getTime().toString()
        );
    }
}
