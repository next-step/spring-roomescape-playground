package roomescape.exception;

public final class DuplicateReservationException extends RuntimeException {

    public DuplicateReservationException() {
        super("이미 예약된 날짜와 시간입니다.");
    }
}
