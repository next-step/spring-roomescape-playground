package roomescape.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ReservationException {

    public NotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST, "[NOT FOUND]" + message);
    }

}
