package roomescape.error;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.error.exception.NotFoundReservationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<CustomErrorResponse> handleNotFoundReservationException(NotFoundReservationException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(e.getStatusCode(), e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(e.getStatusCode()));
    }
}
