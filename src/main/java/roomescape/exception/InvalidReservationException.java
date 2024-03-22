package roomescape.exception;

public class InvalidReservationException extends RuntimeException{
    public InvalidReservationException() {
        super("Invalid Reservation Value");
    }
}
