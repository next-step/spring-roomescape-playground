package roomescape.exception;

public class ReservationNotFoundException extends IllegalArgumentException {

    public ReservationNotFoundException(final String message) {
        super(message);
    }
}
