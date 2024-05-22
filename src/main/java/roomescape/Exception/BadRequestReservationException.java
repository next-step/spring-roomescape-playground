package roomescape.Exception;

public class BadRequestReservationException extends RuntimeException{
    public BadRequestReservationException(String message){
        super(message);
    }
}