package roomescape.exception;

public class NotFoundTimeException extends RuntimeException {
    public NotFoundTimeException() {
        super("time is not found.");
    }
}
