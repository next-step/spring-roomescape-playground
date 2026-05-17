package roomescape.global.controller;

import org.springframework.http.ResponseEntity;

public abstract class ApiException extends RuntimeException {
    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract ResponseEntity<? extends Dto> getResponse();


    public abstract class Dto {
        public abstract String getType();

        public String getMessage() {
            return ApiException.this.getMessage();
        }
    }
}
