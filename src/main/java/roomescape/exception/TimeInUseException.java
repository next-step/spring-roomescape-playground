package roomescape.exception;

public class TimeInUseException extends RuntimeException {
    public TimeInUseException(String message) {
        super(message);
    }
}
