package roomescape.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidReservationArgumentException.class)
    public ResponseEntity<String> handlerInvalidReservationArgumentException(InvalidReservationArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleNotFoundReservationException(NotFoundReservationException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }
}
