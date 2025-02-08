package roomescape.global.exception.code;

import org.springframework.http.HttpStatus;

public enum ErrorStatus {

    INVALID_REQUEST_RESERVATION_INFO(HttpStatus.BAD_REQUEST, "잘못된 예약 정보입니다"),
    INVALID_REQUEST_RESERVATION_ID(HttpStatus.BAD_REQUEST, "예약 아이디를 잘못 입력하였습니다. (0보다 큰 양수)"),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약 정보를 찾지못했습니다. %s");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorStatus(HttpStatus httpStatus, String message) {
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
