package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Reservation Not Found")
public class NotFoundReservationException extends RuntimeException {
    public NotFoundReservationException(String message) {
        super(message);
    }
}
