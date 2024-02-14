package roomescape.exception;

public class NotFindReservationException extends RuntimeException{
    public NotFindReservationException(){
        super("Reservation not found");
    }
}
