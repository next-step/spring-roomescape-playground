package roomescape.exception.customexception;

import roomescape.exception.model.ErrorCode;

public class ReservationNotFoundException extends CustomException {
    public ReservationNotFoundException() {
        super(ErrorCode.RESERVATION_NOT_FOUND);
    }
}
