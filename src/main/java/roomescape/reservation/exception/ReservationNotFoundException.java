package roomescape.reservation.exception;

import roomescape.global.exception.code.ErrorStatus;

public class ReservationNotFoundException extends RuntimeException {
    ErrorStatus errorStatus;

    public ReservationNotFoundException(ErrorStatus errorStatus) {
        this.errorStatus = errorStatus;
    }

    public ErrorStatus getErrorCode() {
        return errorStatus;
    }
}
