package roomescape.error;

public class NotFoundReservationException extends RuntimeException {
    public NotFoundReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
