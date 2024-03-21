package roomescape.exception;

public class NotFoundTimeException extends RuntimeException{
    public NotFoundTimeException() {
        super("Time has not found");
    }
}
