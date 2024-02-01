package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.controller.ReservationController;

@ControllerAdvice(assignableTypes = ReservationController.class)
public class ReservationExceptionHandler {
    @ExceptionHandler(BadRequestReservationException.class)
    public ResponseEntity<Void> handleBadRequestReservationException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundReservationException() {
        return ResponseEntity.badRequest().build(); //NotFoundException(404) returns BadRequest(400)?
    }
}
