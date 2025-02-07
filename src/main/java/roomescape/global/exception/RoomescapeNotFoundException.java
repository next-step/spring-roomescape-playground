package roomescape.global.exception;

import roomescape.global.exception.code.ErrorStatus;

public class RoomescapeNotFoundException extends RoomescapeException {

    public RoomescapeNotFoundException(ErrorStatus errorStatus, Object data) {
        super(errorStatus.getHttpStatus(), String.format(errorStatus.getMessage(), data));
    }
}