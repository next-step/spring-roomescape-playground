package roomescape.exception;

public class NotFoundReservationException extends RuntimeException {
    public NotFoundReservationException() {
        super("reservation is not found.");
    }
}
