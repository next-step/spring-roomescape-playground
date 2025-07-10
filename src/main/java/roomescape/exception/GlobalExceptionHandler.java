package roomescape.exception;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.warn("BusinessException 발생 : {}, {}", e.getCode(), e.getMessage(), e);
        return ResponseEntity
            .status(e.getHttpStatus())
            .body(ErrorResponse.from(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e) {
        Map<String, Object> arguments = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            arguments.put(error.getField(), error.getDefaultMessage());
        }
        return new ErrorResponse(
            "validation.failed",
            "입력값이 올바르지 않습니다.",
            arguments
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.warn("DataIntegrityViolationException 발생 : {}", e.getMessage(), e);
        return new ErrorResponse(
            "data.integrity.violation",
            "요청한 데이터가 무결성 제약 조건을 위반했습니다.",
            Map.of()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUnexpectedException(Exception e) {
        log.error("Unhandled Exception 발생: {}", e.getMessage(), e);
        return new ErrorResponse(
            "internal.server.error",
            "예상치 못한 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
            Map.of()
        );
    }
}
