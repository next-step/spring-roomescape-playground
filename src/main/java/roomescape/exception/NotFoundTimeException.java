package roomescape.exception;

public class NotFoundTimeException extends RuntimeException {
    public NotFoundTimeException() {
        super();
    }

    public NotFoundTimeException(String message) {
        super(message);
    }
}
