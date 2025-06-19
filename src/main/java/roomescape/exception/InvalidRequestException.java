package roomescape.exception;

public class InvalidRequestException extends IllegalArgumentException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
