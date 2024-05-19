package roomescape.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;
import roomescape.exception.ErrorMessage;
import roomescape.exception.ReservationException;
import roomescape.model.Reservation;

public record ReservationRequest(
        String name,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime time
) {

    public ReservationRequest {
        validateRequestField(name,date,time);
    }

    private void validateRequestField(final String name,
                                      final LocalDate date,
                                      final LocalTime time) {
        if (date == null) {
            throw new ReservationException(ErrorMessage.EMPTY_DATE);
        }
        if (name.isEmpty()) {
            throw new ReservationException(ErrorMessage.EMPTY_NAME);
        }
        if (time == null) {
            throw new ReservationException(ErrorMessage.EMPTY_TIME);
        }
    }

    public Reservation convertReservation() {
        return new Reservation(name, date, time);
    }

}
