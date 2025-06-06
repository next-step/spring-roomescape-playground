package roomescape.exception;

public class NotFoundReservationException extends RuntimeException {

    public NotFoundReservationException() {
        super("예약 내역을 찾을 수 없습니다.");
    }
}
