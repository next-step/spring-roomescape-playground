package roomescape.controller;

import java.time.format.DateTimeParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.controller.exception.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Void> notFoundExceptionHandler(NotFoundException error) {
        System.out.println(error.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<Void> illegalArgumentsExceptionHandler(IllegalArgumentException error) {
        System.out.println(error.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
