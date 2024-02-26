package roomescape.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException{

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> handleException (EmptyResultDataAccessException e){
        return ResponseEntity.badRequest().body(ErrorCode.NOT_FOUND_RESERVATION.getMessage());
    }

    @ExceptionHandler(NoParameterException.class)
    public ResponseEntity<String> handleException (NoParameterException e){
        return ResponseEntity.badRequest().body(ErrorCode.NO_PARAMETER.getMessage());
    }
}
