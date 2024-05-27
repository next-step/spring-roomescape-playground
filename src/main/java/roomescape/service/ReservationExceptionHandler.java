package roomescape.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.domain.exception.BadRequestReservation;
import roomescape.service.exception.NoReservationException;

@ControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler
    ResponseEntity<String> badRequestHandler(BadRequestReservation e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<String> NoReservationHandler(NoReservationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
