package roomescape.exception;

import org.springframework.http.HttpStatus;

public class DuplicateReservationException extends RoomEscapeException {

    public DuplicateReservationException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
