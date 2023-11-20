package roomescape._core.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape._core.errors.Exception.Exception400;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception400.class)
  public ResponseEntity<?> badRequest(Exception400 e){
    return ResponseEntity.badRequest().build();
  }

}
