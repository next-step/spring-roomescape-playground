package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;
import roomescape.controller.ResponseInfo;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static class NotFoundReservationException extends RuntimeException {
        public NotFoundReservationException(String message) {
            super(message);
        }
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException e){
        return ResponseEntity.status(ResponseInfo.BAD_REQUEST.getStatus()).body(e.getMessage());
    }
}
