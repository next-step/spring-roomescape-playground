package roomescape.exception;

public class ReservationSaveFailedException extends RuntimeException {
  public ReservationSaveFailedException(String message) {
    super(message);
  }
}
