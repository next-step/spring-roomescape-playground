package roomescape.exception;

import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends RoomEscapeException {

    public ReservationNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
