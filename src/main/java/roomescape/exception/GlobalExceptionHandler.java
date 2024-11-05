package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.InvalidReservationException;
import roomescape.exception.NotFoundReservationException;

@ControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 전역적으로 처리
public class GlobalExceptionHandler {

    // 특정 예외를 처리하는 메서드를 정의
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundReservationException(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<Void> handleInvalidReservationException(InvalidReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}

