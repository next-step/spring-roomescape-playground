package roomescape.exception.customexception;

import roomescape.exception.model.ErrorCode;

public class TimeAlreadyExistsException extends CustomException {
    public TimeAlreadyExistsException() {
        super(ErrorCode.TIME_ALREADY_EXISTS);
    }
}
