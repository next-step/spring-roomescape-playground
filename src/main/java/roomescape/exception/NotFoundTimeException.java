package roomescape.exception;

public class NotFoundTimeException extends NotFoundException {
    public NotFoundTimeException() {
        super("시간을 찾을 수 없습니다.");
    }
}
