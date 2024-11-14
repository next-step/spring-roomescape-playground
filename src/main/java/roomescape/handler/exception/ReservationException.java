package roomescape.handler.exception;

import roomescape.handler.ErrorReasonDto;
import roomescape.handler.ErrorStatus;

public class ReservationException extends BaseException {
  public ReservationException(ErrorStatus errorStatus) {
    super(errorStatus);
  }
}
