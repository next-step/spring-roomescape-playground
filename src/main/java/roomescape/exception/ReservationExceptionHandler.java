package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler(BadRequestReservationException.class)
    public ResponseEntity<Void> handleBadRequest(BadRequestReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}
