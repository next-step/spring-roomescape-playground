package roomescape.exception;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> NotFoundReservationException(NotFoundReservationException ex) {
        return new ResponseEntity<>("Not Found Reservation", HttpStatus.BAD_REQUEST);
    }


}
