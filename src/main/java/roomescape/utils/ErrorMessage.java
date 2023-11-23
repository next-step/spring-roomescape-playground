package roomescape.utils;

public enum ErrorMessage {
    EMPTY_NAME_ERROR(400,"이름을 입력해주세요."),
    EMPTY_DATE_ERROR(400,"날짜를 입력해주세요."),
    EMPTY_TIME_ERROR(400,"시간을 입력해주세요."),
    NON_EXISTING_RESERVATION(400,"일치하는 예약을 찾을 수 없습니다.");
    private static final String prefix = "[%d ERROR] ";
    private final int errorCode;
    private final String message;

    ErrorMessage(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getMessage() {
        return prefix.formatted(errorCode) + message;
    }

}
