package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(RoomEscapeException.class)
    public ResponseEntity<String> handleException(RoomEscapeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
