package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exceptions.EmptyDateTimeException;
import roomescape.exceptions.EmptyNameException;
import roomescape.exceptions.PastDateTimeException;
import roomescape.exceptions.ReservationNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EmptyNameException.class, EmptyDateTimeException.class, PastDateTimeException.class})
    public ResponseEntity<String> handleIllegalReservation(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler({ReservationNotFoundException.class})
    public ResponseEntity<String> handleReservationNotFoundException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}
