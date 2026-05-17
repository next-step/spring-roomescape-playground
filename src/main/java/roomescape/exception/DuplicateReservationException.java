package roomescape.exception;

public class DuplicateReservationException extends RoomescapeException {
    public DuplicateReservationException(String date, String time) {
        super(String.format("해당 날짜(%s)와 시간(%s)은 이미 예약되어 있습니다.", date, time));
    }
}