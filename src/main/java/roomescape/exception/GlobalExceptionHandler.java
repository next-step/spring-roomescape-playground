package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<String> handleNotFoundReservationException(NotFoundReservationException e, WebRequest request) {
        String message = "예약을 찾을 수 없습니다: " + e.getReservationId();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((e.getMessage()));
    }

    @ExceptionHandler(InvalidReservationException.class)
    public ResponseEntity<String> handleInvalidReservationException(InvalidReservationException e, WebRequest request) {
        String message = e.getFieldName() + "'의 값이 유용하지 않습니다: " + e.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("예기치 않은 오류가 발생했습니다: " + e.getMessage());
    }

}
