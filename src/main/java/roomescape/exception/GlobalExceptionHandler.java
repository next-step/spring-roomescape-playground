package roomescape.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFormat(InvalidFormatException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "입력 형식이 올바르지 않습니다.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<Map<String, String>> handleInvalidReservation(InvalidReservationException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundReservation(NotFoundReservationException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
