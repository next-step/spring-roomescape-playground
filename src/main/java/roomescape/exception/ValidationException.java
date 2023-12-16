package roomescape.exception;

import org.springframework.validation.BindingResult;

public class ValidationException extends RuntimeException {

    private BindingResult bindingResult;

    public ValidationException(BindingResult bindingResult) {
        super();
        this.bindingResult = bindingResult;
    }

    public ValidationException(String message, BindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;

    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
