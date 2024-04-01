package roomescape.exception;

import org.springframework.http.HttpStatus;

public class NotFoundReservationException extends BaseException {

    public NotFoundReservationException(ErrorCode errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }

    public NotFoundReservationException(ErrorCode errorCode, Throwable cause) {
        super(HttpStatus.NOT_FOUND, errorCode, cause);
    }
}
