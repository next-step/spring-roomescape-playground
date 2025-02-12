package roomescape.global.exception;

import org.springframework.http.HttpStatus;

public class RoomescapeBadRequestException extends RoomescapeException {

    public RoomescapeBadRequestException(final HttpStatus httpStatus, final String message) {
        super(httpStatus, message);
    }
}
