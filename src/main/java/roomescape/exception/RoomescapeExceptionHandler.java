package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.reservation.BadRequestReservationException;
import roomescape.exception.reservation.NotFoundReservationException;
import roomescape.exception.time.BadRequestTimeException;
import roomescape.exception.time.NotFoundTimeException;

@ControllerAdvice
public class RoomescapeExceptionHandler {
    @ExceptionHandler({BadRequestReservationException.class, BadRequestTimeException.class})
    public ResponseEntity<Void> handleBadRequestReservationException() {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler({NotFoundReservationException.class, NotFoundTimeException.class})
    public ResponseEntity<Void> handleNotFoundReservationException() {
        return ResponseEntity.badRequest().build(); //NotFoundException(404) returns BadRequest(400)?
    }
}
