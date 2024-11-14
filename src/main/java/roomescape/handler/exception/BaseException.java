package roomescape.handler.exception;

import lombok.AllArgsConstructor;
import roomescape.handler.ErrorReasonDto;
import roomescape.handler.ErrorStatus;

@AllArgsConstructor
public class BaseException extends RuntimeException {
  ErrorStatus errorStatus;

  public ErrorReasonDto getErrorReason() {
    return errorStatus.toErrorReasonDto();
  }
}
