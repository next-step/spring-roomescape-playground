package roomescape.exception;

public final class InvalidReservationException extends RuntimeException {

    public InvalidReservationException() {
        super("예약 정보를 모두 입력해야 합니다.");
    }

    public InvalidReservationException(String message) {
        super(message);
    }
}
