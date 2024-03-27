package roomescape.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;

    public BaseException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return exceptionMessage.getMessage();
    }

    public HttpStatus getStatus() {
        return exceptionMessage.getStatus();
    }
}