package roomescape.global;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.global.controller.ApiException;
import roomescape.global.controller.InternalErrorException;
import roomescape.global.dto.ApiFieldError;
import roomescape.global.dto.ApiInputErrorResult;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiInputErrorResult> handleMalformedInput(MethodArgumentNotValidException exception) {
        List<ApiFieldError> errors = exception.getBindingResult().getAllErrors()
                .stream().map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return new ApiFieldError(fieldError.getField(),
                                fieldError.getCode(),
                                fieldError.getDefaultMessage());
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        ApiInputErrorResult result = new ApiInputErrorResult("MalformedInput", "입력 형식이 잘못되었습니다.", errors);

        return ResponseEntity.status(exception.getStatusCode())
                .body(result);
    }

    @ExceptionHandler
    public ResponseEntity<ApiInputErrorResult> handleMalformedInput(HttpMessageNotReadableException exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof JsonMappingException jsonException) {
            String field = jsonException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));

            ApiFieldError fieldError = new ApiFieldError(field, jsonException.getClass().getSimpleName(),
                    jsonException.getLocation().offsetDescription() + "에서 오류가 발생했습니다.");

            return ResponseEntity.badRequest()
                    .body(new ApiInputErrorResult("MalformedInput", "JSON 파싱에 실패했습니다.", List.of(fieldError)));
        }

        throw exception;
    }

    @ExceptionHandler
    public ResponseEntity<? extends ApiException.Dto> handleApiException(ApiException exception) {
        return exception.getResponse();
    }

    @ExceptionHandler
    public void handleUncaughtError(Throwable throwable) throws InternalErrorException {
        throw new InternalErrorException(throwable);
    }
}
