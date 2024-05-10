package roomescape.reservation.domain.exception;

public class NotExistReservationException extends RuntimeException {

    public NotExistReservationException(final String message) {
        super(message);
    }
}
