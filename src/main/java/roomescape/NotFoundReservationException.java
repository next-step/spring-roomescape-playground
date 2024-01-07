package roomescape;

public class NotFoundReservationException extends RuntimeException{
    public NotFoundReservationException() {
        super();
    }
    public NotFoundReservationException(String message) {
        super(message);
    }
}
