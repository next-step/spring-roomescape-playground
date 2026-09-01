package roomescape.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.response.code.ErrorCode;

public enum TimeErrorCode implements ErrorCode {
    INVALID_TIME("INVALID_TIME", HttpStatus.BAD_REQUEST, "예약 시간대가 올바르지 않습니다.");

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
