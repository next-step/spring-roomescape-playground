package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<String> handleInvalidReservationException(InvalidReservationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
