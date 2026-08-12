package roomescape.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFoundReservation(NotFoundReservationException e) {
        System.out.println("[오류] : " + e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(BlankReservationException.class)
    public ResponseEntity<Void> handleBlankReservation(BlankReservationException e) {
        System.out.println("[오류] : " + e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
