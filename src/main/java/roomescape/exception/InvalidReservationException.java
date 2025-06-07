package roomescape.exception;

public class InvalidReservationException extends RuntimeException {

    public InvalidReservationException() {
        super("필수 인자값이 비어있습니다.");
    }
}
