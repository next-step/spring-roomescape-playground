package roomescape.reservation.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.reservation.exception.InvalidReservationRequestException;
import roomescape.reservation.exception.ReservationNotFoundException;

@RestControllerAdvice(basePackages = "roomescape.reservation")  // reservation 패키지 내의 컨트롤러만 처리
public class ReservationExceptionHandler {

    @ExceptionHandler({
        InvalidReservationRequestException.class,
        ReservationNotFoundException.class
    })
    public ResponseEntity<Void> handleBadRequestExceptions() {
        return ResponseEntity.badRequest().build();
    }
}
