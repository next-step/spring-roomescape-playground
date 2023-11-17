package roomescape.utils;

public enum ErrorMessage {
    EMPTY_NAME_ERROR("이름을 입력해주세요."),
    EMPTY_DATE_ERROR("날짜를 입력해주세요."),
    EMPTY_TIME_ERROR("시간을 입력해주세요."),
    NON_EXISTING_RESERVATION("일치하는 예약을 찾을 수 없습니다.");
    private static final String prefix = "[ERROR} ";
    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return prefix + message;
    }

}
