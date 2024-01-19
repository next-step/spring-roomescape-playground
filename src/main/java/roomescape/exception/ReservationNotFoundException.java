package roomescape.exception;

public class ReservationNotFoundException extends RuntimeException{
    public static final String RESERVATION_NOT_FOUND_MESSAGE = "해당 Id의 예약이 존재하지 않습니다";

    public  ReservationNotFoundException(String message) {
        super(message);
    }
}
