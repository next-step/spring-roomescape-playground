package roomescape.exception;

import static roomescape.exception.ExceptionMessage.NOT_EXIST_RESERVATION;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<String> handleException(BaseException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
    }

//    @ExceptionHandler(EmptyResultDataAccessException.class)
//    public ResponseEntity<String> handleException() {
//        return new ResponseEntity<>(NOT_EXIST_RESERVATION.getMessage(), NOT_EXIST_RESERVATION.getStatus());
//    }
}