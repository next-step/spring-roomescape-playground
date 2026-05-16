package roomescape.exception.customexception;

import roomescape.exception.model.ErrorCode;

public class AlreadyReservedException extends CustomException {
    public AlreadyReservedException() {
        super(ErrorCode.ALREADY_RESERVED);
    }
}
