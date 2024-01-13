package roomescape.exception;

public class InvalidReservationException extends RuntimeException{
    public static final String INVALID_RESERVATION_MESSAGE= "유효하지 않은 값의 예약입니다.";

    public InvalidReservationException(String message) {
        super(message);
    }
}
