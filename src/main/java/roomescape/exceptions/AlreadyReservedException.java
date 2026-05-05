package roomescape.exceptions;

public class AlreadyReservedException extends RuntimeException {
    public AlreadyReservedException() {
        super("기존 예약과 시간이 겹칩니다.");
    }
}
