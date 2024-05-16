package roomescape.exception;

public class DatabaseOperationException extends RuntimeException {
    private static final String DEFAULT_ERROR_MESSAGE = "An error occurred while performing database operation";

    public DatabaseOperationException() {
        super(DEFAULT_ERROR_MESSAGE);
    }
    public DatabaseOperationException(String message) {
        super(message);
    }
}
