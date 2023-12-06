package roomescape.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.ReservationTime;

public record CreateReservationTime(
    @NotNull
    @DateTimeFormat(pattern = "hh:mm")
    LocalTime time
) {

    public ReservationTime toReservationTime() {
        return new ReservationTime(null, time);
    }
}
