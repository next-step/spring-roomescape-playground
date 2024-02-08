package roomescape.valid;

public class IllegalReservationException extends IllegalArgumentException{
    public IllegalReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
