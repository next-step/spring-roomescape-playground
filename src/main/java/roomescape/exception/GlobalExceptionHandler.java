package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(IllegalArgumentException exception) {
        return new ExceptionResponse(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleException(MethodArgumentNotValidException exception) {
        return new ExceptionResponse(exception.getFieldError().getDefaultMessage(), LocalDateTime.now());
    }
}
