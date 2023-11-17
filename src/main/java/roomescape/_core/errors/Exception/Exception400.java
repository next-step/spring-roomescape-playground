package roomescape._core.errors.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Exception400 extends RuntimeException {

  public Exception400(String message) {
    super(message);
  }

  public HttpStatus status(){
    return HttpStatus.BAD_REQUEST;
  }
}
