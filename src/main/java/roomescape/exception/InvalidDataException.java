package roomescape.exception;

public class InvalidDataException extends RuntimeException {
    private final ErrorMessage errorMessage;

    public InvalidDataException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
