package roomescape.exception;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleUnhandled(Exception exception) {
        exception.printStackTrace();

        return new ExceptionResponse("서버 오류가 발생했습니다", LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(IllegalArgumentException exception) {
        return new ExceptionResponse(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(HttpMessageNotReadableException exception) {
        if (exception.getCause() instanceof InvalidFormatException e) {
            JsonParser parser = (JsonParser) e.getProcessor();
            String fieldName = parser.getParsingContext().getCurrentName();
            return new ExceptionResponse(fieldName + "의 값이 잘못되었습니다: " + e.getValue(), LocalDateTime.now());
        }
        return new ExceptionResponse("HTTP 메시지를 읽을 수 없습니다", LocalDateTime.now());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(MethodArgumentNotValidException exception) {
        return new ExceptionResponse(exception.getFieldError().getDefaultMessage(), LocalDateTime.now());
    }
}
