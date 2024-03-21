package roomescape.exception;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundReservationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.NotFoundTimeException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity<Void> handleNotFoundException(NotFoundTimeException e) {
        return ResponseEntity.badRequest().build();
    }
}
