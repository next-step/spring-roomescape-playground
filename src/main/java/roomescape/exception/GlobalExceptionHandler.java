package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.domain.InvalidReservationException;
import roomescape.domain.NotFoundReservationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<Void> handleInvalidReservationException(InvalidReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundReservationException(NotFoundReservationException e) {
        return ResponseEntity.notFound().build();
    }
}
