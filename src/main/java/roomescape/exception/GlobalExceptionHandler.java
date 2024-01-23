package roomescape.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleInvalidRequest() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(InvalidReservationException.INVALID_RESERVATION_MESSAGE);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleNotFoundReservation() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ReservationNotFoundException.RESERVATION_NOT_FOUND_MESSAGE);
    }
}
