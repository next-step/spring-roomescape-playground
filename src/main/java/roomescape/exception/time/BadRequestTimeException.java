package roomescape.exception.time;

public class BadRequestTimeException extends RuntimeException {
    public BadRequestTimeException() {
    }

    public BadRequestTimeException(String message) {
        super(message);
    }
}
