package roomescape.exception.customexception;

import roomescape.exception.model.ErrorCode;

public class TimeConflictException extends CustomException {
    public TimeConflictException() {
        super(ErrorCode.TIME_CONFLICT);
    }
}
