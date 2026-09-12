package roomescape.exception;

import org.springframework.http.HttpStatus;

public class TimeNotFoundException extends RoomEscapeException {
    public TimeNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
