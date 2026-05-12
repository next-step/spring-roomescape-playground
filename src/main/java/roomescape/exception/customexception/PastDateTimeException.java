package roomescape.exception.customexception;

import roomescape.exception.model.ErrorCode;

public class PastDateTimeException extends CustomException {
    public PastDateTimeException() {
        super(ErrorCode.PAST_DATETIME);
    }
}
