package roomescape.exception;

import org.springframework.http.HttpStatus;

public class TimeInUseException extends RoomEscapeException {
    public TimeInUseException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
