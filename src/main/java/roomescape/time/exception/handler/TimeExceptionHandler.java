package roomescape.time.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.time.exception.TimeNotFoundException;

@RestControllerAdvice(basePackages = "roomescape.time")
public class TimeExceptionHandler {

    @ExceptionHandler({
        TimeNotFoundException.class
    })
    public ResponseEntity<Void> handleBadRequestExceptions() {
        return ResponseEntity.badRequest().build();
    }
}
