package roomescape.exception;

public class NotFoundReservationException extends IllegalArgumentException {
    public NotFoundReservationException() {
        super("해당 예약을 찾을 수 없습니다.");
    }
}
