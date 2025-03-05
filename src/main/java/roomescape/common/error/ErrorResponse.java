package roomescape.common.error;

import java.util.ArrayList;
import java.util.List;
import org.springframework.validation.BindingResult;

public class ErrorResponse {

    private String description;
    private int status;
    private String code;
    private List<FieldError> errors;

    protected ErrorResponse() {
    }

    private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        this.description = errorCode.getDescription();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.errors = errors;
    }

    private ErrorResponse(ErrorCode errorCode) {
        this.description = errorCode.getDescription();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, FieldError.of(bindingResult));
    }

    public String getDescription() {
        return description;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public List<FieldError> getErrors() {
        return errors;
    }

    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        protected FieldError() {
        }

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(BindingResult bindingResult) {
            List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .toList();
        }

        public String getField() {
            return field;
        }

        public String getValue() {
            return value;
        }

        public String getReason() {
            return reason;
        }
    }
}
