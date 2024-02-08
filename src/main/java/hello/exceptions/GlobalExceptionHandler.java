package hello.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, NotSelectTimeException.class,
            ReferencedTimeException.class})
    public ResponseEntity<Void> handleException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({NotFoundReservationException.class, NotFoundTimeException.class})
    public ResponseEntity<Void> handleTimeException() { return ResponseEntity.notFound().build(); }
}
