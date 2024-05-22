package roomescape;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class CommonExceptionHandler {
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ResponseDto> handleException(NotFoundReservationException e) {
        ResponseDto errorResponse = new ResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(BadRequestCreateReservationException.class)
    public ResponseEntity<ResponseDto> handleException(BadRequestCreateReservationException e) {
        ResponseDto errorResponse = new ResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
