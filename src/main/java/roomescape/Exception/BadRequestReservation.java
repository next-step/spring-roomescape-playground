package roomescape.Exception;

public class BadRequestReservation extends RuntimeException{
    public BadRequestReservation(String message){
        super(message);
    }
}
