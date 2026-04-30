package roomescape.exceptions;

public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException() {
        super("해당 예약을 찾을 수 없습니다.");
    }
}
