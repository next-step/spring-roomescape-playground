package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.BadRequestException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> handleNotFoundException(BadRequestException e) {
        System.out.println("BadRequest occurred: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}