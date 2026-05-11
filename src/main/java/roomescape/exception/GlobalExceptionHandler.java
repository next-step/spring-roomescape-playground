package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundReservationException(NotFoundReservationException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getMessage()));
    }
}
