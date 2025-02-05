package roomescape.global.dto.response;

public class CustomErrorResponse {
    private final int statusCode;
    private final String errorMessage;

    public CustomErrorResponse(final int statusCode, final String errorMessage) {
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
