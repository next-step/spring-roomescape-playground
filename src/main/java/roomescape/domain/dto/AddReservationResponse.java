package roomescape.domain.dto;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record AddReservationResponse(
        Long id,
        String name,
        String date,
        Time time
) {
    public static AddReservationResponse from(Reservation reservation) {
        return new AddReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}
