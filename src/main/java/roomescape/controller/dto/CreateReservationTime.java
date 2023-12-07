package roomescape.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.domain.Time;

public record CreateReservationTime(
    @NotNull
    @DateTimeFormat(pattern = "hh:mm")
    LocalTime time
) {

    public Time toReservationTime() {
        return Time.from(time);
    }
}
