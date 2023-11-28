package roomescape.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.Reservation;

public record CreateReservation(
    @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
    @NotBlank String name,
    @NotNull @DateTimeFormat(pattern = "hh:mm") LocalTime time
) {

    public Reservation toReservation() {
        return new Reservation(null, name, date, time);
    }
}
