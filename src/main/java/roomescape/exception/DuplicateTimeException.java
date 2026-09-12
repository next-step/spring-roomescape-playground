package roomescape.exception;

import org.springframework.http.HttpStatus;

public class DuplicateTimeException extends RoomEscapeException {

    public DuplicateTimeException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
