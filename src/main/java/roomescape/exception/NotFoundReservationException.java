package roomescape.exception;

public class NotFoundReservationException extends RuntimeException{
    public NotFoundReservationException() {
        super("Reservation not found");
    }
}
