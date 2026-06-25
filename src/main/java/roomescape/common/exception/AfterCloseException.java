package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public class AfterCloseException extends BusinessException{
    public AfterCloseException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}