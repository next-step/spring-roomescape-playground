package roomescape.reservation.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.reservation.exception.exception.NoDataException;
import roomescape.reservation.exception.exception.NotFoundReservationException;

@ControllerAdvice
public class ExceptionHandlerData {
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleExceptionNotFound(NotFoundReservationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NoDataException.class)
    public ResponseEntity handleExceptionNoData(NoDataException e) {
        return ResponseEntity.badRequest().build();
    }
}
