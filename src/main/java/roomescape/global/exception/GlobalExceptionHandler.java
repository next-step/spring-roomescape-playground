package roomescape.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ReservationNotFoundException.class)
    protected ResponseEntity<CustomResponseEntity> handleReservationNotFoundException(ReservationNotFoundException reservationNotFoundException) {
        return CustomResponseEntity.toResponseEntity(reservationNotFoundException.getHttpStatus(),
            reservationNotFoundException.getMessage());
    }
    @ExceptionHandler(ReservationInvalidArgumentException.class)
    protected ResponseEntity<CustomResponseEntity> handleReservationNotFoundException(
        ReservationInvalidArgumentException reservationBadRequestException) {
        return CustomResponseEntity.toResponseEntity(reservationBadRequestException.getHttpStatus(),
            reservationBadRequestException.getMessage());
    }
}