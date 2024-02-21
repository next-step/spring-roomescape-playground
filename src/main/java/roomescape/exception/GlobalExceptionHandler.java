package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(Exception e) {

        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {

        return ResponseEntity.internalServerError().body("문제가 발생했습니다!");
    }
}
