package roomescape.exception;

public class NotFoundTimeException extends RuntimeException {
    public NotFoundTimeException(Long id) {
        super("존재하지 않는 시간입니다. id: " + id);
    }
}
