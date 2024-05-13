package roomescape.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ReservationExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleNotFoundReservationException(NotFoundReservationException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BadRequestReservation.class)
    public ResponseEntity handleBadRequestReservation(BadRequestReservation e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}