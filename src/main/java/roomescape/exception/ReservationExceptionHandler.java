package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import roomescape.controller.ReservationController;

@ControllerAdvice(assignableTypes = ReservationController.class)
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestReservationException.class)
    public ResponseEntity<Void> handleBadRequestReservationException() {
        return ResponseEntity.badRequest().build();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundReservationException() {
        return ResponseEntity.badRequest().build(); //NotFoundException(404) returns BadRequest(400)?
    }
}
