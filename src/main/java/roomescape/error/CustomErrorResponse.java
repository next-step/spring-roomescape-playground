package roomescape.error;

public class CustomErrorResponse {
    private final int statusCode;
    private final String errorMessage;

    public CustomErrorResponse(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
