package roomescape.exceptions;

import roomescape.ErrorCode;

public class ReservationNotFoundException extends CustomException {
    public ReservationNotFoundException() {
        super(ErrorCode.RESERVATION_NOT_FOUND);
    }
}
