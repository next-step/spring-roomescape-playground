package roomescape.error.exception;

import org.springframework.http.HttpStatus;

public class InvalidValueException extends NotFoundReservationException{
    public InvalidValueException(String errorMessage) {
        super(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }
}
