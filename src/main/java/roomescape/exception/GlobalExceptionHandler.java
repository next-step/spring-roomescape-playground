package roomescape.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.dto.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(TimeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTimeNotFoundException(TimeNotFoundException e) {
        log.error("TimeNotFoundException occurred:", e);

        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReservationNotFoundException(ReservationNotFoundException e) {
        log.error("ReservationNotFoundException occurred:", e);

        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateTimeException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateTimeException(DuplicateTimeException e) {
        log.error("DuplicateTimeException occurred:", e);

        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TimeInUseException.class)
    public ResponseEntity<ErrorResponse> handleTimeInUseException(TimeInUseException e) {
        log.error("TimeInUseException occurred:", e);

        return new ResponseEntity<>(new ErrorResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream().map((err) -> err.getDefaultMessage()).findFirst().orElse("입력 값 검증 실패");
        log.error("MethodArgumentNotValidException occurred:", e);

        return new ResponseEntity<>(new ErrorResponse(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleFallbackException(Exception e) {
        log.error("Exception occurred:", e);

        return new ResponseEntity<>(new ErrorResponse("내부 서버 오류가 발생했습니다."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
