package roomescape.exception.customexception;

import roomescape.exception.model.ErrorCode;

public class TimeNotFoundException extends CustomException {
    public TimeNotFoundException() {
        super(ErrorCode.TIME_NOT_FOUND);
    }
}
