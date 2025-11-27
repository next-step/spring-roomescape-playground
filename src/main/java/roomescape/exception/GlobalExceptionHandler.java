package roomescape.exception;


import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.dto.ErrorResponse;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidReservationArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidReservationArgumentException(InvalidReservationArgumentException e) {
        return buildErrorResponse(e.getFailMessage());
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundReservationException(NotFoundReservationException e) {
        return buildErrorResponse(e.getFailMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FailMessage fail = FailMessage.BAD_REQUEST;
        return buildErrorResponse(fail);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeParseException(DateTimeParseException e) {
        FailMessage fail = FailMessage.BAD_REQUEST;
        return buildErrorResponse(fail);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDBException(DataAccessException e) {
        FailMessage fail = FailMessage.DATABASE_ERROR;
        return buildErrorResponse(fail);
    }


    private ResponseEntity<ErrorResponse> buildErrorResponse(FailMessage failMessage) {
        return ResponseEntity.status(failMessage.getHttpStatus())
                .body(new ErrorResponse(failMessage.getCode(), failMessage.getMessage()));
    }
}
