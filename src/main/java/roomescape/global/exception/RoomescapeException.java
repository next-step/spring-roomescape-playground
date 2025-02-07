package roomescape.global.exception;

import org.springframework.http.HttpStatus;

public class RoomescapeException extends RuntimeException {

    private final HttpStatus status;

    public RoomescapeException(HttpStatus httpStatus, String message) {
        super(message);
        this.status = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.status;
    }
}
