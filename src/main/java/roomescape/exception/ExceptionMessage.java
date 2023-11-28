package roomescape.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionMessage {
    TEST_EXCEPTION("테스트예외", HttpStatus.valueOf(400));

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
