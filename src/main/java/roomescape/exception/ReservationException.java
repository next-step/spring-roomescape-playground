package roomescape.exception;

import org.springframework.http.HttpStatus;

public abstract class ReservationException extends RuntimeException {

    private final HttpStatus httpStatus;

    protected ReservationException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
