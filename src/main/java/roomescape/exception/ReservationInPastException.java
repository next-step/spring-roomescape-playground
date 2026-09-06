package roomescape.exception;

public class ReservationInPastException extends RuntimeException {
  public ReservationInPastException(String message) {
    super(message);
  }
}
