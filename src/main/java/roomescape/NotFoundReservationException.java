package roomescape;

public class NotFoundReservationException extends RuntimeException {
    public NotFoundReservationException(Long id) {
        super("해당 예약을 찾을 수 없습니다. ID: " + id);
    }
}