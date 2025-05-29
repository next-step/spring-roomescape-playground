package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(InvalidReservationRequestException.class)
    public ResponseEntity<String> handleInvalidReservation(InvalidReservationRequestException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ReservationNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
