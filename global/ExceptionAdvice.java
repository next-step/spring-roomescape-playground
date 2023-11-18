package roomescape.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> businessException(BusinessException e) {
        String errorMessage = getErrorMessage(e.getInvalidValue(), e.getFieldName(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.from(errorMessage));
    }

    public static String getErrorMessage(String invalidValue, String errorField,
                                         String errorMessage) {
        return "[%s] %s: %s".formatted(errorField, invalidValue, errorMessage);
    }

}
