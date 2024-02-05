package roomescape.controller.exception;

import org.springframework.http.HttpStatus;

public class NotFoundReservationException extends RuntimeException {
    private final ErrorCode errorCode;

    public NotFoundReservationException() {
        super(ErrorCode.RESERVATION_NOT_FOUND.getMessage());
        this.errorCode = ErrorCode.RESERVATION_NOT_FOUND;
    }

    public HttpStatus getStatus() {
        return errorCode.getHttpStatus();
    }
}