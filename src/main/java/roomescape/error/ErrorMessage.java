package roomescape.error;

public enum ErrorMessage {
    INVALID_NAME("잘못된 이름 형식입니다."),
    INVALID_DATE("잘못된 날짜 형식입니다."),
    INVALID_TIME("잘못된 시간 형식입니다."),
    NO_RESERVATION("존재하지 않는 예약입니다.");

    private static final String PREFIX = "[ERROR] ";
    private final String message;

    ErrorMessage(String message) {
        this.message = PREFIX + message;
    }

    public String getMessage() {
        return message;
    }
}
