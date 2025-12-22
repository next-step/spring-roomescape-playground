package roomescape.web_layer.controller.exception;

public class NotFoundReservationException extends RuntimeException {
    private final FailMessage failMessage;

    public NotFoundReservationException(FailMessage failMessage) {
        super(failMessage.getMessage());
        this.failMessage = failMessage;
    }

    public FailMessage getFailMessage() {
        return failMessage;
    }
}
