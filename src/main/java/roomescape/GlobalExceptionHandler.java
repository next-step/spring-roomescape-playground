package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<String> handle(Exception e) {
        ErrorCode errorCode = ErrorCode.valueOfException(e);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorCode.getMessage(e));
    }
}
