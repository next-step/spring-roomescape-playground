package roomescape.exception;

public class NotFoundTimeException extends RuntimeException {
    private final FailMessage failMessage;

    public NotFoundTimeException(FailMessage failMessage) {
        super(failMessage.getMessage());
        this.failMessage = failMessage;
    }

    public FailMessage getFailMessage() {
        return failMessage;
    }
}
