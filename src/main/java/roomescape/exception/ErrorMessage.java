package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {
    INVALID_DATE(HttpStatus.BAD_REQUEST, "과거 날짜로 예약할 수 없습니다."),
    RESERVATION_EXISTS(HttpStatus.BAD_REQUEST, "해당 시간에 이미 예약이 존재합니다."),
    INVALID_RESERVATION(HttpStatus.BAD_REQUEST, "유효하지 않은 예약입니다."),

    TIME_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 시간입니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 예약입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
