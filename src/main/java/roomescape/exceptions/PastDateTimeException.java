package roomescape.exceptions;

public class PastDateTimeException extends RuntimeException {
    public PastDateTimeException() {
        super("과거 시간을 예약할 수 없습니다.");
    }
}
