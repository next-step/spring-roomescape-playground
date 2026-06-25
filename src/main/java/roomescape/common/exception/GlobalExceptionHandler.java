package roomescape.common.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleNoElementToDelete(NoSuchElementToDeleteException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseBody.from(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleNoReservationTime(NoSuchReservationTimeException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseBody.from(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleEmptyResultDataAccess(EmptyResultDataAccessException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseBody.from(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleDateTimeUnparsable(DateTimeParseException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseBody.from("The date or time string provided is invalid"));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleBeforeOpen(BeforeOpenException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseBody.from(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleAfterClose(AfterCloseException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseBody.from(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponseBody> handleOverlappingTime(OverlappingReservationTimeException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(ErrorResponseBody.from(e.getMessage()));
    }
}