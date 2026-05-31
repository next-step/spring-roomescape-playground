package roomescape.exception.customexception;

import roomescape.exception.model.ErrorCode;

public class TimeInUseException extends CustomException {
    public TimeInUseException() {
        super(ErrorCode.TIME_IN_USE);
    }
}
