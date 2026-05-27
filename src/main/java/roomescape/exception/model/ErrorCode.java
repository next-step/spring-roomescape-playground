package roomescape.exception.model;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    RESERVATION_CONFLICT(HttpStatus.CONFLICT, "기존 예약과 시간이 겹칩니다."),
    RESERVATION_PAST(HttpStatus.BAD_REQUEST, "과거 시간을 예약할 수 없습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 예약을 찾을 수 없습니다."),

    TIME_CONFLICT(HttpStatus.CONFLICT, "기존 시간과 겹칩니다."),
    TIME_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 시간을 찾을 수 없습니다."),

    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST, "필드 값 검증에 실패했습니다."),

    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "잠시 후 다시 시도해 주세요.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
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
