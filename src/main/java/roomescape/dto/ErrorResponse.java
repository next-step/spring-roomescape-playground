package roomescape.dto;

public class ErrorResponse {

    private final String exceptionType;
    private final String message;

    public ErrorResponse(String exceptionType, String message) {
        this.exceptionType = exceptionType;
        this.message = message;
    }

    public String getException() {
        return exceptionType;
    }

    public String getMessage() {
        return message;
    }
}
