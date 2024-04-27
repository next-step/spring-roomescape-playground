package roomescape;

public class NotFoundReservationException extends RuntimeException {
    public NotFoundReservationException(Long id) {
        super("해당 id의 예약을 찾을 수 없습니다. (id: " + id + ")");
    }

}
