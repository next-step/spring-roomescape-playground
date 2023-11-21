package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.Reservation;

public record CreateReservation(
    @DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate date,
    String name,
    @DateTimeFormat(pattern = "hh:mm") LocalTime time
) {

    public Reservation toReservation(Long id) {
        return new Reservation(id, name, date, time);
    }
}
