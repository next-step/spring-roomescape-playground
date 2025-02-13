package roomescape.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidException.class)
    public ResponseEntity<ErrorResponse> invalidException(InvalidException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Bad Request", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ErrorResponse> notFoundReservation(NotFoundReservationException ex) {
        ErrorResponse errorResponse = new ErrorResponse("Not Found", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

}
