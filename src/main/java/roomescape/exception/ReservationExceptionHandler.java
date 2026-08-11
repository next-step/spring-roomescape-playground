package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler({
            InvalidReservationRequestException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<Void> handleBadRequest() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFound() {
        return ResponseEntity.notFound().build();
    }
}
