package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import roomescape.exception.ReservationInvalidException;
import roomescape.exception.ReservationNotFoundException;
import roomescape.exception.ReservationTimeInUseException;
import roomescape.exception.ReservationTimeNotFoundException;
import roomescape.controller.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationInvalidException.class)
    public ResponseEntity<Void> handleReservationInvalidException(ReservationInvalidException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<Void> handleReservationNotFoundException(ReservationNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ReservationTimeInUseException.class)
    public ResponseEntity<ErrorResponse> handleReservationTimeInUseException(ReservationTimeInUseException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(ReservationTimeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReservationTimeNotFoundException(ReservationTimeNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }
}
