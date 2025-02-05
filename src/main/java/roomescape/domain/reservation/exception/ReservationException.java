package roomescape.domain.reservation.exception;

public class ReservationException extends RuntimeException {
  public ReservationException(String message) {
    super(message);
  }

  public ReservationException() {
  }
}
