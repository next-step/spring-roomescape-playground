package roomescape.exception;

public class ReservationNotFoundException extends RuntimeException{

  public ReservationNotFoundException() {
    super(ErrorMessage.NO_RESERVATION.getMessage());
  }

  public ReservationNotFoundException(String message) {
    super(message);
  }

  public ReservationNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public ReservationNotFoundException(Throwable cause) {
    super(cause);
  }

  public ReservationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
