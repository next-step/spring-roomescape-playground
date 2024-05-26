package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundTimeException;

@ControllerAdvice
public class TimeControllerAdvice {
    @ExceptionHandler(NotFoundTimeException.class)
    public ResponseEntity handleNotFoundTimeException(NotFoundTimeException e){
        return ResponseEntity.badRequest().body(e);
    }
}
