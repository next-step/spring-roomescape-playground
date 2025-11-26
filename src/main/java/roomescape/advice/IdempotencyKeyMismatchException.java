package roomescape.advice;

public class IdempotencyKeyMismatchException extends RuntimeException {

    private final ErrorCode errorCode;

    public IdempotencyKeyMismatchException() {
        super(ErrorCode.IDEMPOTENCY_KEY_MISMATCH.getMessage());
        this.errorCode = ErrorCode.IDEMPOTENCY_KEY_MISMATCH;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}

