package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundReservationException(NotFoundReservationException e) {
        System.out.println("NotFoundReservationException occurred: " + e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(roomescape.exception.IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        System.out.println("IllegalArgumentException occurred: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }

}
