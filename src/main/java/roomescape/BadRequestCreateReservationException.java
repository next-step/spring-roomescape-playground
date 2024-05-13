package roomescape;

public class BadRequestCreateReservationException extends RuntimeException{
    public BadRequestCreateReservationException(String message) {
        super(message);
    }
}
