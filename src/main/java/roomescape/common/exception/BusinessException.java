package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    public BusinessException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    private final HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }
}