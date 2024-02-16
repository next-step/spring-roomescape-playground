package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.NoParameterException;
import roomescape.exception.NotFoundReservationException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoParameterException.class)
    public ResponseEntity<Void> handleNoParameterException(NoParameterException e) {
        return ResponseEntity.badRequest().build();
    }
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundReservationException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}
