package roomescape.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.handler.exception.BaseException;
import roomescape.handler.exception.ReservationException;
import roomescape.handler.exception.TimeException;

@ControllerAdvice
public class
ErrorHandler {

  @ExceptionHandler(BaseException.class)
  public ResponseEntity<?> handleException(BaseException e) {
    HttpStatus status = e.getErrorReason().status();
    String code = e.getErrorReason().code();
    return new ResponseEntity<>(code, status);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class )
  public ResponseEntity<?> handleException(MethodArgumentNotValidException e) {
    throw new ReservationException(ErrorStatus.UNEXPECTED_INPUT);
  }
}
