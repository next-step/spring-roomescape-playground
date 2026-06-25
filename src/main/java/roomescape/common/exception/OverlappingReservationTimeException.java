package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public class OverlappingReservationTimeException extends BusinessException {
    public OverlappingReservationTimeException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}