package hello.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, NotFoundReservationException.class, NotSelectTimeException.class})
    public ResponseEntity<Void> handleException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity<Void> handleTimeException() { return ResponseEntity.notFound().build(); }
}
