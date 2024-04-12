package roomescape.reservation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.reservation.exception.NotFoundReservationException;

@ControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

}
