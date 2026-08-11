package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.BlankReservationException;
import roomescape.exception.NotFoundReservationException;

@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(NotFoundReservationException.class)
  public ResponseEntity<Void> handleNotFoundReservationException(NotFoundReservationException ex){
    System.out.println("[오류] : " + ex.getMessage());
    return ResponseEntity.notFound().build();
  }

  @ExceptionHandler(BlankReservationException.class)
  public ResponseEntity<Void> handleBlankReservationException(BlankReservationException ex){
    System.out.println("[입력값 오류] : " + ex.getMessage());
    return ResponseEntity.badRequest().build();
  }
}
