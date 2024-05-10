package roomescape.reservation.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.reservation.presentation.exception.BadReservationSaveException;

@ControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(BadReservationSaveException.class)
    public ResponseEntity<String> handleBadReservationSaveException(BadReservationSaveException exception) {

        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }
}
