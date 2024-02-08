package roomescape;

public class NotFoundReservationException extends RuntimeException {
    public NotFoundReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
