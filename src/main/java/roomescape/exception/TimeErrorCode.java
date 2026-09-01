package roomescape.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.response.code.ErrorCode;

public enum TimeErrorCode implements ErrorCode {
    TIME_NOT_FOUND("TIME_NOT_FOUND", HttpStatus.NOT_FOUND, "해당 시간대를 찾을 수 없습니다."),
    TIME_INVALID("TIME_INVALID", HttpStatus.BAD_REQUEST, "예약 시간대가 올바르지 않습니다."),
    TIME_CONFLICT("TIME_CONFLICT", HttpStatus.CONFLICT, "해당 예약 시간대가 이미 존재합니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    TimeErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
