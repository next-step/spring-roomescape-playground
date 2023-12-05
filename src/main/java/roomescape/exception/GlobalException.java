package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    private static final String prefix = "[ERROR] ";
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException (MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.badRequest().body(prefix + message);
    }

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity<String> handleNoDataException (NoDataException e) {
        return ResponseEntity.badRequest().body(prefix + e.getMessage());
    }
}
