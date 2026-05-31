package roomescape.exception;

public class NotFoundReservationException extends NotFoundException {
    public NotFoundReservationException() {
        super("예약을 찾을 수 없습니다.");
    }
}
