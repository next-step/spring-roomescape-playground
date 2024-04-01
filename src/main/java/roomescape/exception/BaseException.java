package roomescape.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCode errorCode;

    public BaseException(HttpStatus status, ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = status;
        this.errorCode = errorCode;
    }

    public BaseException(HttpStatus status, ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.status = status;
        this.errorCode = errorCode;
    }
}
