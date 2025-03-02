package roomescape.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.global.dto.response.CustomErrorResponse;
import roomescape.global.exception.RoomScapeException;

import static roomescape.global.exception.ExceptionMessage.INVALID_INPUT_FORMAT;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomScapeException.class)
    public ResponseEntity<CustomErrorResponse> handleRoomScapeException(final RoomScapeException roomScapeException) {
        final HttpStatus status = HttpStatus.valueOf(roomScapeException.getStatusCode());
        final CustomErrorResponse errorResponse = new CustomErrorResponse(
                roomScapeException.getStatusCode(), roomScapeException.getMessage()
        );
        return ResponseEntity.status(status)
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadableException() {
        final CustomErrorResponse errorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                INVALID_INPUT_FORMAT.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
