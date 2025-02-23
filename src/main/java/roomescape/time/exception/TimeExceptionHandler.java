package roomescape.time.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TimeExceptionHandler {

    @ExceptionHandler(TimeAlreadyExistException.class)
    public ResponseEntity<Void> handleException(TimeAlreadyExistException e) {
        return ResponseEntity.badRequest().build();
    }
}
