package roomescape.error;

public class NotFoundTimeException extends RuntimeException {
    public NotFoundTimeException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
