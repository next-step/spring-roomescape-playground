package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(InvalidReservationRequestException.class)
    public ResponseEntity<Void> handleInvalidRequest(InvalidReservationRequestException exception) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Void> handleUnreadableRequest(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFound(NotFoundReservationException exception) {
        return ResponseEntity.notFound().build();
    }
}
