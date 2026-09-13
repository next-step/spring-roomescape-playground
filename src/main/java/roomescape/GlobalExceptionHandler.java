package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            NotFoundReservationException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            InvalidReservationException.class,
            HttpMessageNotReadableException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidRequest() {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse("예약 정보가 올바르지 않습니다."));
    }

    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundTime(
            NotFoundTimeException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(ReservationTimeInUseException.class)
    public ResponseEntity<ErrorResponse> handleReservationTimeInUse(
            ReservationTimeInUseException exception
    ) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(exception.getMessage()));
    }
}
