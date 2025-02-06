package roomescape.reservation.exception;

import roomescape.global.exception.code.ErrorCode;

public class ReservationNotFoundException extends RuntimeException {
    ErrorCode errorCode;

    public ReservationNotFoundException(ErrorCode code) {
        this.errorCode = code;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
