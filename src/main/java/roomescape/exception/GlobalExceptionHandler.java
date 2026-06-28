package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleNotFoundReservationException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UnableReservationTimeException.class)
    public ResponseEntity<String> handleUnableReservationTimeExceptionException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity<String> handleNotFoundTimeException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
