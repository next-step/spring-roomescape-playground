package roomescape.exception.Reservation;

public enum ReservationErrorMessage {

    INVALID_DATA("필요 인자가 존재하지 않습니다."),
    NOT_FOUND("해당 예약이 존재하지 않습니다.");

    private final String message;

    ReservationErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}