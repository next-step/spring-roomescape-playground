package roomescape.exception;

import org.springframework.http.HttpStatus;
import roomescape.global.response.code.ErrorCode;

public enum ReservationErrorCode implements ErrorCode {
    RESERVATION_NOT_FOUND("RESERVATION_NOT_FOUND", HttpStatus.NOT_FOUND, "해당 예약을 찾을 수 없습니다."),
    RESERVATION_CONFLICT("RESERVATION_CONFLICT", HttpStatus.CONFLICT, "이미 예약된 시간입니다."),
    RESERVATION_IN_PAST("RESERVATION_IN_PAST", HttpStatus.BAD_REQUEST, "과거 시간은 예약할 수 없습니다."),
    RESERVATION_INVALID("RESERVATION_INVALID", HttpStatus.BAD_REQUEST, "예약 정보가 올바르지 않습니다.");

    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ReservationErrorCode(String code, HttpStatus httpStatus, String message) {
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
