package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 필요한 인자가 없는 경우
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(
            IllegalArgumentException ex
    ) {
        return ResponseEntity.badRequest().build();
    }

    // 삭제할 예약이 없는 경우
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> handleNoSuchElementException(
            NoSuchElementException ex
    ) {
        return ResponseEntity.notFound().build();
    }
}
