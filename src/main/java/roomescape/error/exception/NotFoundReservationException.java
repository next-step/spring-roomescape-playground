package roomescape.error.exception;

public class NotFoundReservationException extends RuntimeException{
    private final int statusCode;

    public NotFoundReservationException(int statusCode, String errorMessage) {
        super(errorMessage);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
