package roomescape.global.exception;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse("입력값이 올바르지 않습니다.");

        return createResponse(GlobalErrorCode.BAD_REQUEST_ERROR, message);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException() {
        return createResponse(GlobalErrorCode.INVALID_HTTP_MESSAGE_BODY);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatchException() {
        return createResponse(GlobalErrorCode.INVALID_HTTP_MESSAGE_PARAMETER);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return createResponse(errorCode);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
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
