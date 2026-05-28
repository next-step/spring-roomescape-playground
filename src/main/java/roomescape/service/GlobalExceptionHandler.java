package roomescape.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.service.ReservationException.DuplicateTimeException;
import roomescape.service.ReservationException.NotFoundReservationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundReservationException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(DuplicateTimeException.class)
    public ResponseEntity<String> handleDuplicateException(DuplicateTimeException e) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
