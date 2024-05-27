package roomescape.dto;

import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationException;

public record ReservationTimeRequest(
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime time
) {

    public ReservationTimeRequest {
        validateTime(time);
    }

    private void validateTime(final LocalTime time) {
        if (time == null) {
            throw new ReservationException(ErrorMessage.EMPTY_TIME);
        }
    }
}
