package roomescape.dto;

import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import roomescape.advice.ErrorCode;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class ErrorResponse {
    private final String code;
    private final String message;
    private Map<String, String> validation;

    private ErrorResponse(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    private ErrorResponse(ErrorCode errorCode, Map<String, String> validation) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.validation = validation;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode));
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode, BindingResult bindingResult) {
        Map<String, String> validationErrors = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (existing, replacement) -> existing
                ));

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(new ErrorResponse(errorCode, validationErrors));
    }
}