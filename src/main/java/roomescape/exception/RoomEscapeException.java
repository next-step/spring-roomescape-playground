package roomescape.exception;

import org.springframework.http.HttpStatus;

public class RoomEscapeException extends RuntimeException {

    private final HttpStatus status;

    protected RoomEscapeException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
