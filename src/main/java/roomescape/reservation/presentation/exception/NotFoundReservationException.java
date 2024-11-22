package roomescape.reservation.presentation.exception;

public class NotFoundReservationException extends RuntimeException {

    private static final String NOT_FOUND_RESERVATION_MESSAGE = "예악을 찾을 수 없습니다.";

    public NotFoundReservationException() {
        super(NOT_FOUND_RESERVATION_MESSAGE);
    }
}
