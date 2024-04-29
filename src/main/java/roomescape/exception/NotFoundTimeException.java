package roomescape.exception;

public class NotFoundTimeException extends RuntimeException{
    public NotFoundTimeException() {
        super("해당 시간을 찾을 수 없습니다.");
    }
}
