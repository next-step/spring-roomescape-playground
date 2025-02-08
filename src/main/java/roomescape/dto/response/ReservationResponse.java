package roomescape.dto.response;

import roomescape.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        long id,

        String name,

        LocalDate date,

        LocalTime time
) {
    public static ReservationResponse create(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }
}
