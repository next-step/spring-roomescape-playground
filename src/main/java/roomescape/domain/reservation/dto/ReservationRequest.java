package roomescape.domain.reservation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservationTime.domain.ReservationTime;

public record ReservationRequest(@NotNull @NotBlank String name, @NotNull LocalDate date, long time) {

    public Reservation newReservation(final ReservationTime reservationTime) {
        return new Reservation(this.name(), this.date(), reservationTime);
    }
}
