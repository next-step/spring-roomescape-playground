package roomescape.advice;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );
        return ResponseEntity.badRequest().body(errors);
    }


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleIDataIntegrityViolationException(
            DataIntegrityViolationException e
    )
    {
        Map<String, String> error = new HashMap<>();
        error.put("message", "이미 예약이 존재하는 시간은 삭제할 수 없습니다.");

        return ResponseEntity.badRequest().body(error);
    }
}
