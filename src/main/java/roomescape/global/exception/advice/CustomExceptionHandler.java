package roomescape.global.exception.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.global.exception.code.ErrorDto;
import roomescape.reservation.exception.InvalidParameterException;
import roomescape.reservation.exception.ReservationNotFoundException;


@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity handleInvalidParameterException(InvalidParameterException e) {
        ErrorDto errorDto = e.getErrorCode().getErrorReason();
        return ResponseEntity.status(errorDto.status()).body(errorDto.message());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    public ResponseEntity handleReservationNotFoundException(ReservationNotFoundException e) {
        ErrorDto errorDto = e.getErrorCode().getErrorReason();
        return ResponseEntity.status(errorDto.status()).body(errorDto.message());
    }
}
