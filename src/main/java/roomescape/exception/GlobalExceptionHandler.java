package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestReservationException.class)
    public ResponseEntity<Void> handleBadRequest(BadRequestReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFound(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Void> handleDateTimeParse(DateTimeParseException e) {
        return ResponseEntity.badRequest().build();
    }
}
