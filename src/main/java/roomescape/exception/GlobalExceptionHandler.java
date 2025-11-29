package roomescape.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.dto.ErrorResponse;

import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidReservationArgumentException.class)
    public ResponseEntity<ErrorResponse> handleInvalidReservationArgumentException(InvalidReservationArgumentException e) {
        log.error("InvalidReservationArgumentException 발생", e);
        return buildErrorResponse(e.getFailMessage());
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundReservationException(NotFoundReservationException e) {
        log.error("NotFoundReservationException 발생", e);
        return buildErrorResponse(e.getFailMessage());
    }

    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundTimeException(NotFoundTimeException e) {
        log.error("NotFoundTimeException 발생", e);
        return buildErrorResponse(e.getFailMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("MethodArgumentNotValidException 발생", e);
        FailMessage fail = FailMessage.BAD_REQUEST;
        return buildErrorResponse(fail);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ErrorResponse> handleDateTimeParseException(DateTimeParseException e) {
        log.error("DateTimeParseException 발생", e);
        FailMessage fail = FailMessage.BAD_REQUEST;
        return buildErrorResponse(fail);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDBException(DataAccessException e) {
        log.error("DataAccessException 발생", e);
        FailMessage fail = FailMessage.DATABASE_ERROR;
        return buildErrorResponse(fail);
    }


    private ResponseEntity<ErrorResponse> buildErrorResponse(FailMessage failMessage) {
        return ResponseEntity.status(failMessage.getHttpStatus())
                .body(new ErrorResponse(failMessage.getCode(), failMessage.getMessage()));
    }
}
