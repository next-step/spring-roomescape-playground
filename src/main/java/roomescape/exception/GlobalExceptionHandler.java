package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.Reservation.ReservationException;
import roomescape.exception.Time.TimeException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<String> reservationExceptionHandler(ReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(TimeException.class)
    public ResponseEntity<String> timeExceptionHandler(TimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}