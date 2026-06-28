package roomescape.exception;

public class UnableReservationTimeException extends RuntimeException {
    public UnableReservationTimeException() {
        super("reservation time is unable.");
    }
}

