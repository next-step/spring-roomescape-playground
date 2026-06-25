package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public class BeforeOpenException extends BusinessException {
    public BeforeOpenException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}