package error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class HandleException {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity HandleException(Exception400 e) {
        return ResponseEntity.badRequest().build();
    }
}