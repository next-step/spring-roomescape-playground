package roomescape.controller.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import roomescape.domain.Reservation;
import roomescape.domain.Time;

public record ReservationCreate(
    @NotNull @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
    @NotBlank String name,
    @NotNull Long time
) {

    public Reservation toReservation() {
        return new Reservation(null, name, date, new Time(time, null));
    }
}
