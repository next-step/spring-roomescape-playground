package roomescape.reservation.exception;

public class NotFoundReservationException extends IllegalArgumentException {
    public NotFoundReservationException(String message) {
        super(message);
    }
}
