package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException{

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleException (NotFoundReservationException e){
        return ResponseEntity.badRequest().body(ErrorCode.NOT_FOUND_RESERVATION.getMessage());
    }

    @ExceptionHandler(NoParameterException.class)
    public ResponseEntity<String> handleException (NoParameterException e){
        return ResponseEntity.badRequest().body(ErrorCode.NO_PARAMETER.getMessage());
    }
}
