package roomescape.reservation.exception;

import roomescape.global.exception.code.ErrorStatus;

public class InvalidParameterException extends RuntimeException {

    ErrorStatus errorStatus;

    public InvalidParameterException(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    public ErrorStatus getErrorCode() {
        return errorStatus;
    }
}
