package roomescape.exception.customexception;

import roomescape.exception.model.ErrorCode;

public class ReservationPastException extends CustomException {
    public ReservationPastException() {
        super(ErrorCode.RESERVATION_PAST);
    }
}
