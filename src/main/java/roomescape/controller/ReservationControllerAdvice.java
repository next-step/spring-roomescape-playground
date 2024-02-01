package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.domain.NotFoundReservationException;

@ControllerAdvice(assignableTypes = ReservationController.class)
public class ReservationControllerAdvice {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> reservationExceptionHandler(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}