package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.InvalidReservationInputException;
import roomescape.exception.InvalidReservationTimeException;
import roomescape.exception.ReservationNotFoundException;

@ControllerAdvice
public class ReservationControllerAdvice {
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<String> handleReservationNotFoundException(ReservationNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidReservationInputException.class)
    public ResponseEntity<String> handleInvalidReservationInputException(InvalidReservationInputException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InvalidReservationTimeException.class)
    public ResponseEntity<String> handleInvalidReservationTimeException(InvalidReservationTimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
