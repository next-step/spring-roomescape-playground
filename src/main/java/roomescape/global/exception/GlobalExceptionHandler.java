package roomescape.global.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import roomescape.global.response.ErrorResponse;
import roomescape.global.response.code.ErrorCode;
import roomescape.global.response.code.GlobalErrorCode;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse("입력값이 올바르지 않습니다.");

        log.warn("Invalid request value: {}", message);
        return createResponse(GlobalErrorCode.BAD_REQUEST_ERROR, message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.warn("Unreadable request body: {}", exception.getMessage());
        return createResponse(GlobalErrorCode.INVALID_HTTP_MESSAGE_BODY);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.warn("Request parameter type mismatch: name={}, value={}", exception.getName(), exception.getValue());
        return createResponse(GlobalErrorCode.INVALID_HTTP_MESSAGE_PARAMETER);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        ErrorCode errorCode = exception.getErrorCode();

        log.warn("Business exception: code={}", errorCode.getCode());
        return createResponse(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error("Unhandled exception", exception);
        return createResponse(GlobalErrorCode.SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> createResponse(ErrorCode responseCode) {
        return createResponse(responseCode, responseCode.getMessage());
    }

    private ResponseEntity<ErrorResponse> createResponse(
            ErrorCode responseCode,
            String message
    ) {
        return ResponseEntity
                .status(responseCode.getHttpStatus())
                .body(new ErrorResponse(responseCode.getCode(), message));
    }
}
