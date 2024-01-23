package roomescape.controller;
import roomescape.exception.BadRequestException;
import roomescape.exception.NotFoundReservationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}
