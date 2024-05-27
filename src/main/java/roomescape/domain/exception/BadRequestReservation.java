package roomescape.domain.exception;

public class BadRequestReservation extends RuntimeException {

    public BadRequestReservation(String message) {
        super(message);
    }
}
