package roomescape.exception.time;

public class NotFoundTimeException extends RuntimeException{
    public NotFoundTimeException() {
    }

    public NotFoundTimeException(String message) {
        super(message);
    }
}
