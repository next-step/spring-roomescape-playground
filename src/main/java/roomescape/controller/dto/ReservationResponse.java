package roomescape.controller.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationResponse(
    long id,
    String name,
    @JsonFormat(pattern = "yyyy-MM-dd") LocalDate date,
    TimeResponse time
) {
    public static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            TimeResponse.from(reservation.getTime())
        );
    }
}
