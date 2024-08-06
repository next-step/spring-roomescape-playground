package roomescape.exception;

public class ReservationNotFoundException extends RuntimeException{

  public ReservationNotFoundException() {
    super(ErrorMessage.NO_RESERVATION.getMessage());
  }
}
