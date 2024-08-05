package roomescape.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.ReservationNotFoundException;

@ControllerAdvice // 전역 예외 처리
public class GlobalExceptionHandler {

  @ExceptionHandler(ReservationNotFoundException.class) // @ExceptionHandler 로 특정 예외 처리
  public ResponseEntity<String> handleNotFoundReservationException(ReservationNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    // 404 Not Found 상태 코드를 반환하고, 예외 메시지를 응답 본문에 포함
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
    // 400 Bad Request 상태 코드를 반환하고, 예외 메시지를 응답 본문에 포함
  }
}
