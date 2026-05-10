package roomescape.exceptions;

import roomescape.ErrorCode;

public class AlreadyReservedException extends CustomException {
    public AlreadyReservedException() {
        super(ErrorCode.ALREADY_RESERVED);
    }
}
