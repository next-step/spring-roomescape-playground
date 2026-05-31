package roomescape.exception.customexception;

import roomescape.exception.model.ErrorCode;

public class ReservationConflictException extends CustomException {
    public ReservationConflictException() {
        super(ErrorCode.RESERVATION_CONFLICT);
    }
}
