package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Void> handleInvalidRequest(InvalidRequestException e) {
        System.out.println("InvalidRequestException 발생 : " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> reservationNotFound(NotFoundException e) {
        System.out.println("NotFoundException 발생 : " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
