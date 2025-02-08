package roomescape.domain.reservation.exception;

import roomescape.global.exception.RoomescapeBadRequestException;
import roomescape.global.exception.code.ErrorStatus;

public class InvalidParameterException extends RoomescapeBadRequestException {

    public InvalidParameterException(final ErrorStatus errorStatus) {
        super(errorStatus.getHttpStatus(), errorStatus.getMessage());
    }
}
