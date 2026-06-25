package roomescape.common.exception;

import org.springframework.http.HttpStatus;

public class NoSuchElementToDeleteException extends BusinessException {
    public NoSuchElementToDeleteException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}