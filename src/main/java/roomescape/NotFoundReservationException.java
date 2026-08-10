package roomescape;

public class NotFoundReservationException extends RuntimeException {

    public NotFoundReservationException(Long id) {
        super("예약을 찾을 수 없습니다. id=" + id);
    }
}
