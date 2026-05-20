package roomescape.reservation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleBadRequest(NotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
