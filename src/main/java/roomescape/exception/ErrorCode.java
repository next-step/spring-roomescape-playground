package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약을 찾을 수 없습니다."),
    TIME_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 시간입니다."),

    DUPLICATE_TIME(HttpStatus.CONFLICT, "이미 존재하는 시간입니다."),
    INVALID_RESERVATION_TIME(HttpStatus.CONFLICT, "과거 시간은 예약할 수 없습니다."),

    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터베이스 처리 중 오류가 발생했습니다.");

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
