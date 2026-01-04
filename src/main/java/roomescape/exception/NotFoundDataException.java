package roomescape.exception;

public class NotFoundDataException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public NotFoundDataException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
