package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "필수 항목이 누락되었거나 유효하지 않은 값입니다."),
    INVALID_DATE_TIME_FORMAT(HttpStatus.BAD_REQUEST, "날짜 또는 시간 형식이 올바르지 않습니다."),
    NOT_FOUND_RESERVATION(HttpStatus.BAD_REQUEST, "예약을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorMessage(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
