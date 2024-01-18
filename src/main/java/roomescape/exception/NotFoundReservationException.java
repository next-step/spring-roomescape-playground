package roomescape.exception;

public class NotFoundReservationException extends RuntimeException{
    public NotFoundReservationException() {
    }

    public NotFoundReservationException(String message) {
        super(message);
    }
}
