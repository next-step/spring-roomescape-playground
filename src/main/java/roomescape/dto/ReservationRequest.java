package roomescape.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationException;

public record ReservationRequest(
        String name,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @JsonProperty("time")
        Long timeId
) {

    public ReservationRequest {
        validateRequestField(name, date, timeId);
    }

    private void validateRequestField(final String name,
                                      final LocalDate date,
                                      final Long timeId) {
        if (name.isEmpty()) {
            throw new ReservationException(ErrorMessage.EMPTY_NAME);
        }
        if (date == null) {
            throw new ReservationException(ErrorMessage.EMPTY_DATE);
        }
        if (timeId < 1L) {
            throw new ReservationException(ErrorMessage.INVALID_TIME_ID);
        }
    }

}
