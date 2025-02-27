package roomescape.global.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.global.exception.RoomescapeException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(RoomescapeException.class)
    public ResponseEntity handleInvalidParameterException(final RoomescapeException exception) {
        exception.printStackTrace();
        return ResponseEntity.status(exception.getHttpStatus())
                .body(exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(final Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidFormatException(final HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}
