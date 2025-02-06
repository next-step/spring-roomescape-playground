package roomescape.domain.reservation.exception;

import roomescape.common.error.ErrorCode;
import roomescape.common.error.exception.InvalidValueException;

public class ReservationException extends InvalidValueException {

    public ReservationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ReservationException(ErrorCode errorCode) {
        super(errorCode);
    }

}
