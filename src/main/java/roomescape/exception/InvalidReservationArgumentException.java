package roomescape.exception;

public class InvalidReservationArgumentException extends RuntimeException {
    private final FailMessage failMessage;

    public InvalidReservationArgumentException(FailMessage failMessage) {
        super(failMessage.getMessage());
        this.failMessage = failMessage;
    }

    public FailMessage getFailMessage() {
        return failMessage;
    }
}
