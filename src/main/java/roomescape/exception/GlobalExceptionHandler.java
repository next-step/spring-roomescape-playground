package roomescape.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
        Map<String, String> error = new HashMap<>();

        if (e.getCause() instanceof InvalidFormatException) {
            error.put("error", "InvalidFormat Exception : 입력 형식이 올바르지 않습니다.");
        } else {
            error.put("error", "HTTP Not Readable Excetption : 요청 형식이 올바르지 않습니다.");
        }

        return ResponseEntity.badRequest().body(error);
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
