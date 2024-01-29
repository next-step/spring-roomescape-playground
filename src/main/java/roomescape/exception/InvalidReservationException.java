package roomescape.exception;

public class InvalidReservationException extends RuntimeException{
    public InvalidReservationException() {
        super("Wrong Value");
    }
}
