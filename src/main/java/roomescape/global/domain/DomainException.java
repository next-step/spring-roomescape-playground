package roomescape.global.domain;

public abstract class DomainException extends RuntimeException {
    public static class UnknownError extends DomainException {
        public UnknownError(String message) {
            super(message);
        }

        public UnknownError(String message, Throwable cause) {
            super(message, cause);
        }

        public UnknownError(Throwable cause) {
            super(cause);
        }
    }


    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public DomainException(Throwable cause) {
        super(cause);
    }
}
