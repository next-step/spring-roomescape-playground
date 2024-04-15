package roomescape.exception;

import org.springframework.http.HttpStatus;

public class NotFoundTimeException extends BaseException {

    public NotFoundTimeException(ErrorCode errorCode) {
        super(HttpStatus.NOT_FOUND, errorCode);
    }

    public NotFoundTimeException(ErrorCode errorCode, Throwable cause) {
        super(HttpStatus.NOT_FOUND, errorCode, cause);
    }
}
