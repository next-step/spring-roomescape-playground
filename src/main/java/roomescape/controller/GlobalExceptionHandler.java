package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.ReservationInvalidException;
import roomescape.exception.ReservationNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationInvalidException.class)
    public ResponseEntity<Void> handleReservationInvalidException(ReservationInvalidException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Void> handleReservationNotFoundException(ReservationNotFoundException e) {
        return ResponseEntity.notFound().build();
    }
}
