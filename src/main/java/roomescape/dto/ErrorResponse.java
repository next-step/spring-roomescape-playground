package roomescape.dto;

public class ErrorResponse {

    private final String exception;
    private final String message;

    public ErrorResponse(String exception, String message) {
        this.exception = exception;
        this.message = message;
    }

    public String getException() {
        return exception;
    }

    public String getMessage() {
        return message;
    }
}
