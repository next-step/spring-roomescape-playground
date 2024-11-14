package roomescape.handler.exception;

import roomescape.handler.ErrorReasonDto;
import roomescape.handler.ErrorStatus;

public class TimeException extends BaseException {
  public TimeException(ErrorStatus errorStatus) {
    super(errorStatus);
  }
}
