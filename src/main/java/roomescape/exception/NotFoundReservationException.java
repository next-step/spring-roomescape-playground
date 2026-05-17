package roomescape.exception;

public class NotFoundReservationException extends RoomescapeException {
    public NotFoundReservationException(Long id) {
        super("해당 예약을 찾을 수 없습니다. ID: " + id);
    }
}