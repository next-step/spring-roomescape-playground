package roomescape.exception;

public class ReservationTimeInUseException extends RuntimeException {
    public ReservationTimeInUseException(String message) {
        super(message);
    }

    public ReservationTimeInUseException(String message, Throwable cause) {
        super(message, cause);
    }
}
