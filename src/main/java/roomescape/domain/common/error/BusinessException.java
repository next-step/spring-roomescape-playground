package roomescape.domain.common.error;

public class BusinessException extends ApplicationException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException() {
    }
}
