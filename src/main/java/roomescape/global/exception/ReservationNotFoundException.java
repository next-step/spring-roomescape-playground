package roomescape.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ReservationNotFoundException extends RuntimeException {

    private HttpStatus httpStatus;

    public ReservationNotFoundException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
