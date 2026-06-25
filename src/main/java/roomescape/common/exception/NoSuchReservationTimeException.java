package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public class NoSuchReservationTimeException extends BusinessException {
    public NoSuchReservationTimeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}