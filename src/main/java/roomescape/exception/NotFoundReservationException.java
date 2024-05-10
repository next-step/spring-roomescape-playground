package roomescape.exception;

public class NotFoundReservationException extends Exception {
    public NotFoundReservationException(){
        super();
    }

    public NotFoundReservationException(String message){
        super(message);
    }
}
