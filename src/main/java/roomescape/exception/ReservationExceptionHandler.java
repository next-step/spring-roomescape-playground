package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ReservationValidationException.class)
    public ResponseEntity<Void> handleException(ReservationValidationException e) {
        return ResponseEntity.badRequest().build();
    }
}
