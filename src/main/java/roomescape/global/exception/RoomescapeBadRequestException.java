package roomescape.global.exception;

import org.springframework.http.HttpStatus;

public class RoomescapeBadRequestException extends RoomescapeException {

    public RoomescapeBadRequestException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }
}
