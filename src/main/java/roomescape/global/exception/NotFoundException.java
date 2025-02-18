package roomescape.global.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RoomScapeException {
    public NotFoundException(final String errorMessage) {
        super(HttpStatus.NOT_FOUND.value(), errorMessage);
    }
}
