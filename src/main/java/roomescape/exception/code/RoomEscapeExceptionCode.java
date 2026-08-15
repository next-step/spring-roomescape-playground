package roomescape.exception.code;

public enum RoomEscapeExceptionCode {

    RESERVATION_NOT_FOUND("해당 예약을 찾을 수 없습니다."),
    ;

    private final String message;

    RoomEscapeExceptionCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
