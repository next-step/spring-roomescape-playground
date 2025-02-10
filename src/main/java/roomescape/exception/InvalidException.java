package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request")
public class InvalidException extends IllegalArgumentException {
    public InvalidException(String message) {
        super(message);
    }
}
