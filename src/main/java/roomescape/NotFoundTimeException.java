package roomescape;

public class NotFoundTimeException extends RuntimeException {

    public NotFoundTimeException() {
        super("예약 시간을 찾을 수 없습니다.");
    }
}
