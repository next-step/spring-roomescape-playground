package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.BadRequestReservationException;
import roomescape.exception.NotFoundReservationException;

@ControllerAdvice(assignableTypes = ReservationController.class)
public class ExceptionController {

    @ExceptionHandler(BadRequestReservationException.class)
    public ResponseEntity<Void> handleBadRequestReservationException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundReservationException() {
        return ResponseEntity.badRequest().build(); //NotFoundException(404) returns BadRequest(400)?
    }
}
