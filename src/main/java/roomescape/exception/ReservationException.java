package roomescape.exception;

public class ReservationException extends RuntimeException {

    private final ErrorMessage errorMessage;

    public ReservationException(ErrorMessage message) {
        this.errorMessage = message;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
