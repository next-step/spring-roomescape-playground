package roomescape.domain.exception;

public class NotFoundReservationException extends RuntimeException{
    public NotFoundReservationException(String message) {
        super(message);
    }
}
