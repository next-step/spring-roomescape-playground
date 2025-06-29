package roomescape.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import roomescape.domain.Reservation;

public record ResponseReservation(
        Long id,
        String name,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @JsonFormat(pattern = "HH:mm")
        LocalTime time
) {

    public static ResponseReservation from(final Reservation reservation) {
        return new ResponseReservation(
                reservation.id(),
                reservation.name(),
                reservation.date(),
                reservation.time().time()
        );
    }
}
