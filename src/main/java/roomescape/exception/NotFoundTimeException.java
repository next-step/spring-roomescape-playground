package roomescape.exception;

public class NotFoundTimeException extends RuntimeException {

    public NotFoundTimeException(Long id) {
        super("시간을 찾을 수 없습니다. id=" + id);
    }
}
