package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.dto.ErrorResponse;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler({
            InvalidReservationRequestException.class,
            InvalidTimeRequestException.class
    })
    public ResponseEntity<ErrorResponse> handleBadRequest(RuntimeException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadableMessage() {
        return ResponseEntity.badRequest().body(new ErrorResponse("입력 형식을 확인해 주세요."));
    }

    @ExceptionHandler({
            NotFoundReservationException.class,
            NotFoundTimeException.class
    })
    public ResponseEntity<Void> handleNotFound() {
        return ResponseEntity.notFound().build();
    }
}
