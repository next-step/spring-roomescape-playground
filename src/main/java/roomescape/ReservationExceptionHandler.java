package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.InvalidRequestException;
import roomescape.exception.NotFoundReservationException;

@ControllerAdvice
public class ReservationExceptionHandler {
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity handleInvalidRequestException(InvalidRequestException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleNotFoundReservationException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

}
