package roomescape.error;

public enum ErrorMessage {
    INVALID_NAME("잘못된 이름 형식입니다."),
    INVALID_DATE_TIME("잘못된 날짜와 시간 형식입니다."),
    INVALID_FUTURE_TIME("예약 시간은 현재 시각 이후여야 합니다."),
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
