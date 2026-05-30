package roomescape.reservations.dto.response;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import roomescape.timeslot.model.Timeslot;

import java.time.LocalDate;

public record ReservationResponse(
        @NotNull
        @PositiveOrZero
        Long id,
        String name,
        LocalDate date,
        Timeslot timeslotId
) {
}
