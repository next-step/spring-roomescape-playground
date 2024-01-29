package roomescape.Exception;

public class NotFindReservationException extends RuntimeException{
    public NotFindReservationException(){
        super("Reservation not found");
    }
}
