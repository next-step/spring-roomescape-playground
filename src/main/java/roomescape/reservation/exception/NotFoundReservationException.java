package roomescape.reservation.exception;

public class NotFoundReservationException extends RuntimeException {

    public NotFoundReservationException() {
        super("Reservation not found");
    }

}
