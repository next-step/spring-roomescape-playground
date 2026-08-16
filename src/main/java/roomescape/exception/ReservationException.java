package roomescape.exception;

import roomescape.global.exception.BusinessException;
import roomescape.global.response.code.ErrorCode;

public class ReservationException extends BusinessException {

    public ReservationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
