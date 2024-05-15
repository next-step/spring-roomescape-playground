package roomescape.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import roomescape.exception.NotFoundReservationException;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ReservationControllerAdvice {
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity handleNotFoundReservationException(NotFoundReservationException e){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException ex) {
        return ResponseEntity.badRequest().body("날짜/시간 형식이 잘못되었습니다.");
    }
}
