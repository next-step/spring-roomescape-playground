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
    public ResponseEntity<CustomErrorResponse> handleBaseException(RoomScapeException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                e.getStatusCode(), e.getMessage()
        );
        HttpStatus status = HttpStatus.valueOf(e.getStatusCode());
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> handleHttpMessageNotReadableException() {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                INVALID_INPUT_FORMAT.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
