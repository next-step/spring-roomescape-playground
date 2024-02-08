package roomescape.controller.exception;

public class InvalidReservationException extends RuntimeException {
    public InvalidReservationException(String message) {
        super(message);
    }
}