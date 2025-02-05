package roomescape.global.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RoomScapeException {
    public BadRequestException(final String errorMessage) {
        super(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }
}
