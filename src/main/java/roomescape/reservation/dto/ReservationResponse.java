package roomescape.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.reservation.domain.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationResponse(
        Long id,
        String name,
        LocalDate date,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
        LocalTime time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time()
        );
    }
}
