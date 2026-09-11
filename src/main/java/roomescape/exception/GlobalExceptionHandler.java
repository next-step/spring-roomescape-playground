package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundReservationException(NotFoundReservationException e) {
        System.out.println("NotFoundReservationException occurred: " + e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(roomescape.exception.IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        System.out.println("IllegalArgumentException occurred: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        System.out.println("HttpMessageNotReadableException occurred: " + e.getMessage());
        return ResponseEntity.badRequest().body("Invalid request.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println("MethodArgumentNotValidException occurred: " + e.getMessage());
        return ResponseEntity.badRequest().body("Invalid request.");
    }
}
