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

    @ExceptionHandler(BadRequestReservationException.class)
    public ResponseEntity handleBadRequestReservation(BadRequestReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(BadRequestTimeException.class)
    public ResponseEntity handleBadRequestTimeException(BadRequestTimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}