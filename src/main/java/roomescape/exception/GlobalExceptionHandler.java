package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //404 Not Found 처리
    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<String> invalidException(InvalidException ex) {
        return new ResponseEntity<>("Invalid value entered", HttpStatus.BAD_REQUEST);
    }

    //
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> notFoundReservation(NotFoundReservationException ex){
        return new ResponseEntity<>("The reservation isn't exist", HttpStatus.NOT_FOUND);
    }


}
