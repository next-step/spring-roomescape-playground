package roomescape.exception.customexception;

import roomescape.exception.model.ErrorCode;

public class PastDateTimeException extends CustomException {
    public PastDateTimeException() {
        super(ErrorCode.RESERVATION_PAST_DATETIME);
    }
}
