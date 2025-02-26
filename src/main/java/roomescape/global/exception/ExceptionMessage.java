package roomescape.global.exception;

public enum ExceptionMessage {

    //Bad Request
    INVALID_NAME("잘못된 이름 양식입니다."),
    INVALID_DATE("잘못된 날짜 양식입니다."),
    INVALID_TIME("잘못된 시간 양식입니다."),
    INVALID_DATETIME("예약 가능 시간은 현재 이후여야 합니다."),
    INVALID_INPUT_FORMAT("잘못된 입력 양식입니다."),
    RESERVATION_NOT_EXISTS("존재하지 않는 예약 내역입니다."),
    RESERVATION_ALREADY_EXISTS("해당 시간에 예약이 이미 존재합니다."),
    TIME_NOT_EXISTS("존재하지 않는 시간입니다."),
    TIME_ALREADY_EXISTS("이미 존재하는 시간입니다."),
    ;

    private static final String ERROR_PREFIX = "[ERROR] ";

    private final String message;

    ExceptionMessage(String message) {
        this.message = ERROR_PREFIX + message;
    }

    public String getMessage() {
        return message;
    }
}
