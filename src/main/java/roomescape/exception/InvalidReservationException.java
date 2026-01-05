package roomescape.exception;

public class InvalidReservationException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public InvalidReservationException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
