package roomescape.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import roomescape.exception.DatabaseOperationException;
import roomescape.exception.ReservationNotFoundException;

@ControllerAdvice
public class ReservationControllerAdvice {
    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity handleReservationNotFoundException(ReservationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity handleDatabaseOperationException(DatabaseOperationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
