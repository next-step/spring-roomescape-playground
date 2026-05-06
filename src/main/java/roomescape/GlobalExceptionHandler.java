package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        System.out.println("잘못된 요청입니다: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundReservationException(NotFoundReservationException e) {
        System.out.println("예약을 찾을 수 없습니다: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<Void> handleDuplicateReservationException(DuplicateReservationException e) {
        System.out.println("중복 예약 오류: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Void> handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        System.out.println("경로 변수 타입이 올바르지 않습니다: " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}