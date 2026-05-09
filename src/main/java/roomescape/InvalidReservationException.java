package roomescape;

public class InvalidReservationException extends RoomescapeException {
    public InvalidReservationException(String reason) {
        super("잘못된 예약 요청입니다: " + reason);
    }
}