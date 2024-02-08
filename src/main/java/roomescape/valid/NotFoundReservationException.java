package roomescape.valid;

public class NotFoundReservationException extends IllegalArgumentException {
    public NotFoundReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
