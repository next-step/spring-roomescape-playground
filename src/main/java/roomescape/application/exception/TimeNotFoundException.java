package roomescape.application.exception;

public class TimeNotFoundException extends IllegalArgumentException {

    public TimeNotFoundException(final String message) {
        super(message);
    }
}
