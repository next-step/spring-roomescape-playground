package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionMessage {
    NOT_EXIST_RESERVATION("존재하지 않는 예약입니다.", HttpStatus.valueOf(400)),
    NOT_EXIST_FACTOR("인자가 존재하지 않습니다", HttpStatus.valueOf(400));

    private final String message;
    private final HttpStatus httpStatus;

    ExceptionMessage(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
}