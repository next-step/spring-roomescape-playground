package roomescape;

public class InvalidReservationException extends RuntimeException {

    public InvalidReservationException() {
        super("예약 정보가 올바르지 않습니다.");
    }
}
