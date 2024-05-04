package roomescape;

public class NotFoundReservationException extends RuntimeException{
    public NotFoundReservationException(String message) {
        super(message);
    }
}
