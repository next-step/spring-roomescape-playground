package roomescape.exception.handler;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.customexception.CustomException;
import roomescape.exception.model.ErrorCode;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.METHOD_ARGUMENT_NOT_VALID;
        List<String> messages = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
                .toList();

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(errorCode.getHttpStatus(), errorCode.getMessage());
        detail.setProperty("messages", messages);

        return detail;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ErrorCode errorCode = ErrorCode.HTTP_MESSAGE_NOT_READABLE;
        return ProblemDetail.forStatusAndDetail(errorCode.getHttpStatus(), errorCode.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ProblemDetail handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ProblemDetail.forStatusAndDetail(errorCode.getHttpStatus(), errorCode.getMessage());
    }

    @ExceptionHandler
    public ProblemDetail handleUnHandledException(Exception e) {
        ErrorCode errorCode = ErrorCode.UNEXPECTED_ERROR;
        logger.error(e.getMessage());
        return ProblemDetail.forStatusAndDetail(errorCode.getHttpStatus(), errorCode.getMessage());
    }
}
