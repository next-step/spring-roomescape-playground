package roomescape.exception;

public class BadRequestReservationException extends RuntimeException {
    public BadRequestReservationException() {
    }

    public BadRequestReservationException(String message) {
        super(message);
    }
}
