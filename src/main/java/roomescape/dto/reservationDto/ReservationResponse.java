package roomescape.dto.reservationDto;

import roomescape.model.Reservation;
import roomescape.model.Time;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        long id,
        String name,
        LocalDate date,
        Time time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }
}

