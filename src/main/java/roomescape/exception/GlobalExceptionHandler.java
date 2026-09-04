package roomescape.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import roomescape.dto.ErrorResponse;

import java.sql.SQLException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundReservation(NotFoundReservationException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(BlankReservationException.class)
    public ResponseEntity<ErrorResponse> handleBlankReservation(BlankReservationException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 요청입니다. id는 숫자여야 합니다."));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMessageNotReadable(HttpMessageNotReadableException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("요청 형식이 올바르지 않습니다."));
    }

    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundTime(NotFoundTimeException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(BlankTimeException.class)
    public ResponseEntity<ErrorResponse> handleBlankTime(BlankTimeException e) {
        log.info(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleDuplicateTimeException(DuplicateTimeException e) {
        log.warn(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.error(e.getMessage());
        Throwable cause = e.getRootCause();

        if (cause instanceof SQLException sqlException) {
            if (sqlException.getErrorCode() == 23505) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("이미 존재하는 시간대입니다."));
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("잘못된 요청입니다."));
    }
}
