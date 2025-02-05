package roomescape.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.global.dto.response.CustomErrorResponse;
import roomescape.global.exception.RoomScapeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomScapeException.class)
    public ResponseEntity<CustomErrorResponse> handleBaseException(RoomScapeException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                e.getStatusCode(), e.getMessage()
        );
        HttpStatus status = HttpStatus.valueOf(e.getStatusCode());
        return new ResponseEntity<>(errorResponse, status);
    }
}
