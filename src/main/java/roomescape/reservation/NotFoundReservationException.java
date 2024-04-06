package roomescape.reservation;

public class NotFoundReservationException extends RuntimeException {

    public NotFoundReservationException() {
        super("Reservation not found");
    }

}
