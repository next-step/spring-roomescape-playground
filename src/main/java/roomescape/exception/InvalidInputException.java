package roomescape.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends ReservationException {

    public InvalidInputException(String message) {
        super(HttpStatus.BAD_REQUEST, "[INVALID_INPUT]" + message);
    }

}
