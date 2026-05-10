package roomescape.exceptions;

import roomescape.ErrorCode;

public class PastDateTimeException extends CustomException {
    public PastDateTimeException() {
        super(ErrorCode.PAST_DATETIME);
    }
}
