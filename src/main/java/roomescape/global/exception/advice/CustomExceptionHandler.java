package roomescape.global.exception.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.global.exception.RoomescapeException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RoomescapeException.class)
    public ResponseEntity handleInvalidParameterException(final RoomescapeException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).body(exception.getMessage());
    }
}
