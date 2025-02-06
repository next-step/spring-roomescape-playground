package roomescape.common.error;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String description;
    private int status;
    private String code;
    private List<FieldError> errors;

    private ErrorResponse(ErrorCode errorCode, List<FieldError> errors) {
        this.description = errorCode.getDescription();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.errors = errors;
    }

    private ErrorResponse(final ErrorCode errorCode) {
        this.description = errorCode.getDescription();
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.errors = new ArrayList<>();
    }

    public static ErrorResponse of(final ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(final ErrorCode errorCode, final BindingResult bindingResult) {
        return new ErrorResponse(errorCode, FieldError.of(bindingResult));
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .toList();
        }
    }
}
