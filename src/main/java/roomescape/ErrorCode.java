package roomescape;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    ALREADY_RESERVED(HttpStatus.BAD_REQUEST, "기존 예약과 시간이 겹칩니다."),
    PAST_DATETIME(HttpStatus.BAD_REQUEST, "과거 시간을 예약할 수 없습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 예약을 찾을 수 없습니다.");

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
