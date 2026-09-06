package roomescape.exception;

public class DuplicateReservationTimeException extends RuntimeException {
  public DuplicateReservationTimeException(String message) {
    super(message);
  }
}
