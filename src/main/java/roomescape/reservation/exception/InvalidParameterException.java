package roomescape.reservation.exception;

import roomescape.global.exception.code.ErrorCode;

public class InvalidParameterException extends RuntimeException {

    ErrorCode errorCode;

    public InvalidParameterException(ErrorCode code) {
        this.errorCode = code;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
