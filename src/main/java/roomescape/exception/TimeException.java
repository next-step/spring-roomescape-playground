package roomescape.exception;

import roomescape.global.exception.BusinessException;
import roomescape.global.response.code.ErrorCode;

public class TimeException extends BusinessException {

    public TimeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
